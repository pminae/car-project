package com.green.car.service;

import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.TokenDto;
import com.green.car.entity.Member;
import com.green.car.repository.MemberRepository;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Member saveMember(MemberJoinDto dto) {
        //사용자가 전달한 값을 가진 dto 객체를 member타입으로 리턴
        Member member = dtoToEntity(dto);
        //회원가입 폼을 통해 받은 password 암호화하기
        //패스워드 필드는 전달한 패스워드 값을 암호화하여 할당
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        return memberRepository.save(member);
    }

    //아이디 중복체크
    @Override
    public String validateDuplicateMember(MemberJoinDto dto) {
        Member findMember = memberRepository.findByEmail(dto.getEmail());
        //이미 동일한 이메일을 가진 member가 존재함
        if(findMember!=null){
            return "fail";
        }
        return "ok";
    }

    @Override
    public TokenDto login(String memberId, String password) {
        //로그인 요청시 전달된 memberId(email)과 password를 기반으로
        //Authentication 객체 생성
        //authenticate()메소드를 통해 요청된 Member에 대한 검증을 진행
        //->loadUserByUsername메소드가 실행됨
        //검증에 성공하면 Authentication 객체를 기반으로 JWT 토큰 생성

        //1. AuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(memberId, password);
        //2. 검증
        //authenticate 메소드가 실행될 때 요청된 Member에 대한 검증 진행
        //CustomUserDetailsService에서 구현한 loadUserByUsername 메소드 실행
        Authentication authentication = authenticationManagerBuilder
                .getObject().authenticate(authenticationToken);
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        return tokenDto;
    }
}
