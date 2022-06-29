package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.global.common.response.ResponseService;
import com.itech.guide.global.common.response.SingleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;

    public SingleResult<MemberResponse> myProfile(String email){
        return responseService.getSingleResult(MemberResponse.from(
                memberRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.error("[error] ProfileService.findOne : 유저를 찾을 수 없습니다.");
                    return new IllegalArgumentException("유저를 찾을 수 없습니다.");
                }
        )));
    }


}
