package com.itech.guide.domain.member.vo;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ReIssueRequest {

    @NotBlank(message = "name 필드가 없습니다.")
    private String name;
    @NotBlank(message = "refreshToken 필드가 없습니다.")
    private String refreshToken;

}
