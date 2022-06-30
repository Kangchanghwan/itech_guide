package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Transactional
public class InitMember {

    private final MemberRepository memberRepository;


    @PostConstruct
    public void initRoleData(){
        if(memberRepository.count() > 100) return;
        for(int i = 0 ; i < 100 ; i ++){
            Member member = new Member(null,"lgodl1598"+i+"@naver.com","123123","test1", i,null);
            memberRepository.save(member);
        }
    }

}
