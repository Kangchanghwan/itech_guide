package com.itech.guide.domain.member.entity;

import com.itech.guide.domain.member.vo.SignUpRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * 1. 객체생성은 Builder 패턴과 static 생성자만 허용한다.
 * 2. 기본생성자는 Protected 접근지시자를 사용한다.
 * 3. 도메인에서 @Data 는 사용하지 않는다.
 * 4. @ToString 은 무한 참조가 생길 수 있으므로 of 를 사용하여 필요한 필드만 사용한다.
 * 5. 테이블의 이름은 반드시 명시한다.
 * 6. 클래스 상단에 @Builder X 생성자 상단에 @Builder O
 * 7. 컬럼의 이름도 반드시 지정하여 사용한다.
 * 8. 도메인 주도 설계(DDD) 객체 자신이 포함하고 있는 데이터를 조작하는데 필요한 행동을 정의한다.
 * 9. 무책임한 @Setter 사용은 금지한다.
 * 10. JPA @Embedded 를 활용하여 도매인 객체의 책임을 나누어 사용한다.
 * 11. 줄임말을 지양하고 정확한 이름을 사용한다.
 * */

@Entity
@Getter
@Table(name="member_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {


    public static Member from(SignUpRequest request){
        Member member = new Member();
        member.name = request.getName();
        member.password = request.getPassword();
        member.age = request.getAge();
        member.email = request.getEmail();
        return member;
    }



    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_email",nullable = false, unique = true ,length = 200)
    private String email;
    @Column(name ="member_pwd",nullable = false,length = 200)
    private String password;
    @Column(name ="member_name", nullable = false , length = 50)
    private String name;
    @Column(name = "member_age",nullable = false)
    private int age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Roles> roles = new ArrayList<>();

    public void addRole(Roles role){
        this.roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
