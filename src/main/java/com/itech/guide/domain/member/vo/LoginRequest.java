package com.itech.guide.domain.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@NoArgsConstructor

public class LoginRequest {
    @Length(max = 50, min = 1, message = "1글자 이상 50글자 이하로 입력하세요.")
    @NotBlank(message = "name 필드가 없습니다.")
    private String name;
    @Length(max = 100, min = 8, message = "8글자 이상 100글자 이하로 입력하세요.")
    @NotBlank(message ="password 필드가 없습니다.")
    private String password;

}
