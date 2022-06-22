package com.itech.guide.domain.member.api.v1;

import com.itech.guide.domain.member.application.MemberSignUpRestService;
import com.itech.guide.domain.member.domain.Member;
import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.dto.SignUpRequest;
import com.itech.guide.domain.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "MemberApi")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApi {

    private final MemberSignUpRestService memberSignUpRestService;
    private final MemberRepository memberRepository;

    @Operation(description = "회원 가입")
    @PostMapping("/members")
    public ResponseEntity<MemberResponse> signUp( @RequestBody SignUpRequest request){
        return new ResponseEntity<>(memberSignUpRestService.join(request), HttpStatus.CREATED);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> profile(@PathVariable Long id){

        Member member = memberRepository.findById(id).orElseThrow(
                () -> {throw new IllegalArgumentException("해당하는 사용자가 없습니다.");}
        );
        MemberResponse response = MemberResponse.builder()
                                    .id(member.getId())
                                    .age(member.getAge())
                                    .name(member.getName())
                                    .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }




}
