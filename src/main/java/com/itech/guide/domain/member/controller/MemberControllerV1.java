package com.itech.guide.domain.member.controller;

import com.itech.guide.domain.member.dto.LoginResponse;
import com.itech.guide.domain.member.dto.MemberResponse;
import com.itech.guide.domain.member.entity.AuthMember;
import com.itech.guide.domain.member.service.MemberAccountService;
import com.itech.guide.domain.member.service.MemberListService;
import com.itech.guide.domain.member.service.MemberProfileService;
import com.itech.guide.domain.member.service.MemberSignUpRestService;
import com.itech.guide.domain.member.vo.LoginRequest;
import com.itech.guide.domain.member.vo.ReIssueRequest;
import com.itech.guide.domain.member.vo.SignUpRequest;
import com.itech.guide.global.common.response.CommonResult;
import com.itech.guide.global.common.response.ListResult;
import com.itech.guide.global.common.response.SingleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * ======== URI 명명 규칙 ===========
 * 1. URI는 정보의 자원을 표현해야한다.
 * 2. 자원에 대한 행위는 HTTP METHOD(GET, POST, PUT, DELETE)로 표기한다.
 * 3. 슬래시 구분자는(/) 계층관계를 나타내는데 사용한다.
 * 4. URI 마지막 문자로 슬래시(/)를 포함하지 않는다.
 * 5. 하이픈(-)은 URI 가독성을 높이는데 사용한다.
 * 6. 밑줄(_)은 URI에 사용하지 않는다.
 * 7. URI 경로에는 소문자가 적합하다.
 * 8.파일 확장자는 URI에 포함시키지 않는다.
 * */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberControllerV1 {

    private final MemberSignUpRestService memberSignUpRestService;
    private final MemberProfileService memberProfileService;
    private final MemberAccountService memberAccountService;
    private final MemberListService memberListService;


    @PostMapping("/re-issue")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<LoginResponse> reIssue(@Valid @RequestBody ReIssueRequest reIssueRequest) {
        return memberAccountService.reIssueAccessToken(reIssueRequest);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public CommonResult logout(@AuthenticationPrincipal AuthMember member, HttpServletRequest request) {
        return memberAccountService.logout(member.getUsername(),
                        request.getHeader("Authorization").substring(7));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<LoginResponse> login(@Valid @RequestBody LoginRequest loginDto) {
        return memberAccountService.login(loginDto);
    }

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResult<MemberResponse> signUp(@RequestBody @Valid SignUpRequest request){
        return memberSignUpRestService.join(request);
    }

    @PreAuthorize("#email == authentication.principal.username or hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/members/{email}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<MemberResponse> profile( @PathVariable String email){
        log.info("[LOG] MemberControllerV1.profile : {}",email);
        return memberProfileService.myProfile(email);
    }

    @GetMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public ListResult<MemberResponse> memberList(){
        log.info("[LOG] MemberControllerV1.memberList");
        return memberListService.findAll();
    }


}
