package com.green.car.service;

import com.green.car.constant.Role;
import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.TokenDto;
import com.green.car.entity.Member;

import java.awt.*;

public interface MemberService {
    //회원 가입하기
    Member saveMember(MemberJoinDto dto);
    //이메일 체크
    String validateDuplicateMember(MemberJoinDto dto);
    //로그인하기
    TokenDto login(String memberId, String password);

    //dto -> Entity
    default Member dtoToEntity(MemberJoinDto dto){
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .role(Role.USER)
                .build();
        return member;
    }
}
