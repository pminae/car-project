package com.green.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    //아이디와 비밀번호가 일치하면 클라이언트에게 전달해줄 토큰
    //토큰은 암호화된 문자열이라 모두 String
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
