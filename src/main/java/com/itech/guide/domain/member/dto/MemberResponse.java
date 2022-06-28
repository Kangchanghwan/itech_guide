package com.itech.guide.domain.member.dto;

import com.itech.guide.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private Long id;
    private String email;
    private String name;
    private int age;

    public static MemberResponse from (Member member) {
       return new MemberResponse(member.getId(),member.getEmail(),member.getName(), member.getAge());
    }
}
