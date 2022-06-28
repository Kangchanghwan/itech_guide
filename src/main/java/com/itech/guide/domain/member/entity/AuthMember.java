package com.itech.guide.domain.member.entity;

import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Objects;

public class AuthMember extends User {

    private final Member member;

    public AuthMember(Member member) {
        super(member.getName(), member.getPassword(), member.getRoles());
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthMember that = (AuthMember) o;
        return Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), member);
    }

}
