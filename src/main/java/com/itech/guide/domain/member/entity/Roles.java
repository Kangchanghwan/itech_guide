package com.itech.guide.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Table(name ="role_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Roles implements GrantedAuthority {

    @Id
    @Column(name="role_id")
    private String id;


    @Enumerated(value = EnumType.STRING)
    @Column(name="roles_role")
    private Role role;

    public static Roles newInstance(Role role){
        return new Roles(role.getCode(),role);
    }


    @Override
    public String getAuthority() {
        return role.name();
    }
}
