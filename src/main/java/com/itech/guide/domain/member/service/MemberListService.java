package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.global.common.response.ListResult;
import com.itech.guide.global.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberListService {

   private final MemberRepository memberRepository;
   private final ResponseService responseService;

   public ListResult<MemberResponse> findAll(PageRequest request){
       Page<Member> all = memberRepository.findAll(request);

       return responseService.getListResult(
               memberRepository.findAll(request)
                       .stream()
                       .map(MemberResponse::from)
                       .collect(Collectors.toList()));
   }

}
