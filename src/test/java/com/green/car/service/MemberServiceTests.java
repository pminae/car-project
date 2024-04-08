package com.green.car.service;

import com.green.car.dto.MemberJoinDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    MemberService memberService;


    @Test
    public void insertMember(){
        MemberJoinDto dto = MemberJoinDto.builder()
                .name("이수정")
                .email("su@green.com")
                .address("울산시 동구")
                .password("1234")
                .build();
        memberService.saveMember(dto);
    }
}
