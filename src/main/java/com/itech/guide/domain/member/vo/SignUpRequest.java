package com.itech.guide.domain.member.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class SignUpRequest {

    @Length(max = 50, min = 1, message = "1글자 이상 50글자 이하로 입력하세요.")
    @NotBlank(message = "name 필드가 없습니다.")
    private String name;
    @Length(max = 100, min = 8, message = "8글자 이상 100글자 이하로 입력하세요.")
    @NotBlank(message ="password 필드가 없습니다.")
    private String password;

    private int age;


}
