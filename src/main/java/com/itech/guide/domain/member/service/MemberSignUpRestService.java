package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.global.error.exception.BadRequestException;
import com.itech.guide.domain.member.vo.SignUpRequest;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.global.common.response.ResponseCode;
import com.itech.guide.global.common.response.ResponseDto;
import com.itech.guide.global.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class MemberSignUpRestService {

    private  final ResponseService responseService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseDto> join(SignUpRequest request) {
        checkNameDuplicate(request.getName());
        checkPasswordConvention(request.getPassword());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = Member.from(request);
        member.addRole("ROLE_USER");
        if(memberRepository.save(member) == null)
            responseService.send(ResponseCode.F_SIGNUP);

        return responseService.sendData(ResponseCode.S_OK,MemberResponse.from(member));

    }

    private void checkNameDuplicate(String name) {
        boolean isDuplicate = memberRepository.existsByName(name);
        if(isDuplicate) {
            throw new BadRequestException("이미 존재하는 회원입니다.");
        }
    }

    private void checkPasswordConvention(String password) {
        // TODO: Check Password Convention
    }

}
