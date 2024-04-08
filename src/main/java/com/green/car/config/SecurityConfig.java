package com.green.car.config;

import com.green.car.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //HttpSecurity를 구성하여 보안 설정을 정의
        //csrf(Cross-Site Request Forgery) 보안을 비활성화
        //.sessionManagement jwt을 사용하므로 세션을 사용하지 않음

        http.csrf(cs->cs.disable())
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //요청에 대한 인가 규칙 설정
        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("/**").permitAll()
                .requestMatchers("/car/register").hasRole("DEALER")
                .requestMatchers("/member/test").hasRole("USER")
                .anyRequest().authenticated()
        );

        //필터 추가
        //user.....필터 앞에 만들어준 JwtAuthenticationFilter 필터를 추가해서 먼저 JWT 인증을 처리하도록 설정
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
