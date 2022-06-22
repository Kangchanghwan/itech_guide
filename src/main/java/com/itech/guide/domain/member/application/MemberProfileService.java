package com.itech.guide.domain.member.application;

import com.itech.guide.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberRepository memberRepository;


}
