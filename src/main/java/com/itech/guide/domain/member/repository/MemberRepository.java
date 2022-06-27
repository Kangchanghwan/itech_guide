package com.itech.guide.domain.member.repository;

import com.itech.guide.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByName(String username);

    boolean existsByName(String name);
}
