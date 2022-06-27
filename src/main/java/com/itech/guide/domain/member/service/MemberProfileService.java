package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.global.common.response.ResponseCode;
import com.itech.guide.global.common.response.ResponseDto;
import com.itech.guide.global.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;

    public ResponseEntity<ResponseDto> myProfile(Long id){

        Member member = memberRepository.findById(id).orElseThrow(
                () -> {
                    log.error("[error] ProfileService.findOne : 유저를 찾을 수 없습니다.");
                    return new IllegalArgumentException("유저를 찾을 수 없습니다.");
                }
        );
        return responseService.sendData(ResponseCode.S_OK, MemberResponse.from(member));
    }


}
