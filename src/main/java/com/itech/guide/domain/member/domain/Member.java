package com.itech.guide.domain.member.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 1. 객체생성은 Builder 패턴만 허용한다.
 * 2. 기본생성자는 Protected 접근지시자를 사용한다.
 * 3. 도메인에서 @Data 는 사용하지 않는다.
 * 4. @ToString 은 무한 참조가 생길 수 있으므로 of 를 사용하여 필요한 필드만 사용한다.
 * 5. 테이블의 이름은 반드시 명시한다.
 * 6. 클래스 상단에 @Builder X 생성자 상단에 @Builder O
 * 7. 컬럼의 이름도 반드시 지정하여 사용한다.
 * 8. 도메인 주도 설계(DDD) 객체 자신이 포함하고 있는 데이터를 조작하는 데 필요한 행동을 정의한다.
 * 9. 무책임한 @Setter 사용은 금지한다.
 * 9. JPA @Embedded 를 활용하여 도매인 객체의 책임을 나누어 사용한다.
 * */

@EqualsAndHashCode(of = {"id"})
@ToString(of = "name")
@Entity
@Getter
@Table(name="member_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Builder
    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="member_name", unique = true , nullable = false , length = 50)
    private String name;
    @Column(name = "member_age",nullable = false)
    private int age;



}
