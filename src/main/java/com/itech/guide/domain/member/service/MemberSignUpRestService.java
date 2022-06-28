package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.domain.member.entity.Role;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.domain.member.repository.RoleRepository;
import com.itech.guide.domain.member.vo.SignUpRequest;
import com.itech.guide.global.common.response.ResponseService;
import com.itech.guide.global.common.response.SingleResult;
import com.itech.guide.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSignUpRestService {

    private final ResponseService responseService;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SingleResult<MemberResponse> join(SignUpRequest request) {
        checkNameDuplicate(request.getEmail());
        checkPasswordConvention(request.getPassword());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = Member.from(request);
        member.addRole(roleRepository.findById(Role.ROLE_TEMPORARY_MEMBER.getCode()).get());
        memberRepository.save(member);
        return responseService.getSingleResult(MemberResponse.from(member));

    }

    private void checkNameDuplicate(String email) {
        boolean isDuplicate = memberRepository.existsByEmail(email);
        if(isDuplicate) {
            throw new BadRequestException("이미 존재하는 회원입니다.");
        }
    }

    private void checkPasswordConvention(String password) {
        // TODO: Check Password Convention
    }

}
