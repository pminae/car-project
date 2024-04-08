package com.green.car.Controller;

import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.MemberLoginDto;
import com.green.car.dto.TokenDto;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final MemberService memberService;

    //로그인
    @PostMapping("/login")
    public TokenDto login(@RequestBody MemberLoginDto memberLoginDto){
        TokenDto token = memberService.login(memberLoginDto.getUsername(), memberLoginDto.getPassword());
        return token;
    }

    //회원가입
    @PostMapping("/join")
    public String memberJoin(@RequestBody MemberJoinDto memberJoinDto){
        try{
            memberService.saveMember(memberJoinDto);
            return "ok";
        }catch (Exception e){
            return "fail";
        }
    }

    @PostMapping("/usertest")
    public String usertest(){
        //securityContextHolder안에 securityContext 안에 Authentication(인증객체) 꺼내오기
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null){
            throw new RuntimeException("인증 정보가 없습니다.");
        }
        //로그인한 사용자의 이메일을 받을 수 있다.
        return authentication.getName();
    }

    @PostMapping("/test")
    public String test(){
        return "success";
    }
}
