package com.itech.guide.domain.member.entity;

import lombok.Getter;

import javax.persistence.Enumerated;


@Getter
public enum Role {

    ROLE_ADMIN("20","관리자"),
    ROLE_SUPER_ADMIN("10","슈퍼 관리자"),
    ROLE_MEMBER("1","회원"),
    ROLE_TEMPORARY_MEMBER("2","임시회원");

    private String code;
    private String name;

    Role(String code, String name) {

        this.code = code;
        this.name = name;

    }

}
