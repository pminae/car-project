package com.green.car.service;

import com.green.car.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtTokenProvider {

    //필드 ---> 상수이기 때문에 초기화를 해 줘야 함 여기서는 생성할 때 초기화
    private final Key key;

    //생성자
    //@Value 어노테이션으로 application.properties에 있는 값에 접근해서 받아올 수 있다.
    //jwt.secret를 받아와서 secretKey에 매핑
    //생성자 secretKey를 디코딩하여 JWT 서명에 사용할 키를 생성
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        //의존 주입 된 secretKey를 BASE64 디코딩하여 바이트 배열로 변환
        byte[] ketByte = Decoders.BASE64.decode(secretKey);
        //변환된 바이트 배열로 hmacShaKey를 생성 ---> jwt서명을 생성하고 검증하는데 사용
        this.key = Keys.hmacShaKeyFor(ketByte);
    }

    //메소드
    //로그인된 사용자에게서 accessToken을 받아서 Claims를 돌려줌
    //토큰 중 사용자 정보가 들어있는 부분만 리턴
    //주어진 accessToken을 해동하고 클래임 객체를 리턴함.
    private Claims parseClaims(String accessToken){
        //Jwts.parserBuilder() JWT를 파싱하는 빌더를 생성
        //setSigningKey(key) 서명키를 설정 JWT 검증시 사용
        //.parseClaimsJws(accessToken) access 토큰을 파싱해서 서명을 확인
        //.getBody() jwt 본문을 얻는다.---> claim(jwt의 정보를 포함하는 부분)을 반환

        try{
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessToken).getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }

    //유저 정보를 가지고 AccessToken, RefreshToken을 생성하는 메소드
    //authentication을 서버가 주기는 하는데, 요청이 끝나면 바로 없어진다.
    public TokenDto generateToken(Authentication authentication){
        //권한 가져오기 (user, admin, dealer)
        String authorities = authentication.getAuthorities().stream()
                //객체에서 권한을 추출해서 문자열 컬렉션 리턴
                .map(GrantedAuthority::getAuthority)
                //하나의 문자열로 리턴
                .collect(Collectors.joining(","));
        //long타입의 현재 시간
        //토큰의 유효시간을 정하기 위해 필요하다. (지금시간으로부터 하루)
        long now = (new Date()).getTime();  //현재 데이트 객체를 밀리초로 리턴
        //Access Token 생성
        Date accessTokenExpiresIn = new Date(now+86400000); //현재 시간에 하루를 밀리초단위로 변환해서 더한값
        String accessToken = Jwts.builder()
                //JWT에 주체를 설정, 주체는 주로 사용자의 식별자 (이메일 OR 아이디...)
                .setSubject(authentication.getName())
                //JWT에 사용자의 권한 정보를 추가
                .claim("roll", authorities)
                //토큰의 만료 시간 설정 (하루)
                .setExpiration(accessTokenExpiresIn)
                //서명 추가하기, 서명은 JWT의 무결성을 보장, 위조방지용
                //HS256알고리즘을 사용하여 비밀키를 이용해 서명을 생성
                .signWith(key, SignatureAlgorithm.HS256)
                //JWT를 문자열로 반환
                .compact();

        //Refresh 토큰 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                //토큰의 타입을 나타내는 속성
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //JWT 복호화하여 토큰에 들어있는 정보를 꺼내는 메소드
    //Authentication 리턴
    //Authentication 스프링 시큐리티에서 인증된 정보를 담고 있는 인터페이스
    //accessToken을 전달 받아 Authentication (인증 객체)를 리턴
    public Authentication getAuthentication(String accessToken){
        //토큰 복호화
        Claims claims = parseClaims(accessToken);
        if(claims.get("roll")==null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        //권한 정보가 담겨있는 collection을 authorities가 받는다.
        Collection<? extends GrantedAuthority> authorities =
                //"USER,ADMIN" ---> split ---> {"USER", "ADMIN"}
                Arrays.stream(claims.get("roll").toString().split(","))
                        //람다식, 생성자를 이용한다.
                        //각각의 권한 정보를 SimpleGrantedAuthority 객체로 변환 (생성자를 통해)
                        //map("user"->new SimpleGrantedAuthority("user"))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //Authentication
                                            //이메일,           //password는 빈문자
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }

    //토큰 정보를 검증하는 메소드
    public boolean validateToken(String token){
        //Jwts.parserBuilder() : JWT 파싱하는 빌더를 생성
        //setSigningkey(key) : 서명 키를 설정, JWT를 검증할 때 사용
        //build() Jwtparser 객체를 빌드함
        //.parseClaimsJws(token) : 주어진 토큰을 파싱하여 서명을 확인하고 JWT 본문 반환
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("유효하지 않는 JWT Token", e);
        } catch(ExpiredJwtException e) {
            log.info("만료된 JWT Token", e);
        } catch(UnsupportedJwtException e) {
            log.info("입증되지 않은 JWT Token", e);
        } catch(IllegalArgumentException e){
            log.info("JWT Claims가 비어 있습니다", e);
        }
        return false;

    }
}
