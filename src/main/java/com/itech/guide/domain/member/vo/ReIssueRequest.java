package com.itech.guide.domain.member.vo;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ReIssueRequest {

    @NotBlank(message = "email 필드가 없습니다.")
    @Email(message = "email 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "refreshToken 필드가 없습니다.")
    private String refreshToken;

}
