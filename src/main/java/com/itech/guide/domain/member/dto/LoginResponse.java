package com.itech.guide.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(String accessToken,String refreshToken){
        return new LoginResponse(accessToken, refreshToken);
    }
}
