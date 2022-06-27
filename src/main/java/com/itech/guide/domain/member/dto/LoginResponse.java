package com.itech.guide.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class LoginResponse {

    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(String accessToken,String refreshToken){
        return new LoginResponse(accessToken, refreshToken);
    }
}
