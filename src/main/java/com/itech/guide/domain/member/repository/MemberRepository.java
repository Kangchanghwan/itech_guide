package com.itech.guide.domain.member.repository;

import com.itech.guide.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {


    @Query("select m from Member m join fetch m.roles where m.email = :email")
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    @Override
    Page<Member> findAll(Pageable pageable);
}
