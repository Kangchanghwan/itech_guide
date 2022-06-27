package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.entity.AuthMember;
import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.global.error.exception.BadRequestException;
import com.itech.guide.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new BadRequestException("토큰을 확인하세요."));
        return new AuthMember(member);
    }
}
