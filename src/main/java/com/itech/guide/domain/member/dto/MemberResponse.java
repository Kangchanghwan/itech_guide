package com.itech.guide.domain.member.dto;

import com.itech.guide.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
public class MemberResponse {

    private Long id;
    private String name;
    private int age;

    public static MemberResponse from (Member member) {
        MemberResponse response = new MemberResponse();
        response.id = member.getId();
        response.name = member.getName();
        response.age = member.getAge();
        return response;
    }
}
