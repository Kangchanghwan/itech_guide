package com.itech.guide.domain.member.application;

import com.itech.guide.domain.member.domain.Member;
import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.dto.SignUpRequest;
import com.itech.guide.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class MemberSignUpRestService {

    private final MemberRepository memberRepository;

    public MemberResponse join(SignUpRequest request){

        Member member = Member.builder()
                .name(request.getName())
                .age(request.getAge())
                .build();

        memberRepository.save(member);

        MemberResponse response = MemberResponse.builder()
                .age(member.getAge())
                .name(member.getName())
                .build();

        return response;

    }


}
