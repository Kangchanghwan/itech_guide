package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.global.common.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberListService {

   private final MemberRepository memberRepository;
   private final ResponseServiceV2 responseService;

   public ListResult<MemberResponse> findAll(){
       return responseService.getListResult(
               memberRepository.findAll()
                       .stream()
                       .map(member -> MemberResponse.from(member))
                       .collect(Collectors.toList()));
   }

}
