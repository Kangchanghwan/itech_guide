package com.itech.guide.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberResponse {

    private Long id;
    private String name;
    private int age;

}
