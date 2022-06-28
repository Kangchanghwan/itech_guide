package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.dto.LoginResponse;
import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.domain.member.vo.LoginRequest;
import com.itech.guide.domain.member.vo.ReIssueRequest;
import com.itech.guide.global.common.response.CommonResult;
import com.itech.guide.global.common.response.ResponseService;
import com.itech.guide.global.common.response.SingleResult;
import com.itech.guide.global.config.security.JwtProvider;
import com.itech.guide.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberAccountService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ResponseService responseService;


    public SingleResult<LoginResponse> login(LoginRequest loginRequest) {
        Member member = memberRepository
                .findByEmail(loginRequest.getEmail()).orElseThrow(() -> new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));
        checkPassword(loginRequest.getPassword(), member.getPassword());
        String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRoles());
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(), member.getRoles());

        return responseService.getSingleResult(LoginResponse.of(accessToken,refreshToken));
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");
        }
    }

    public SingleResult<LoginResponse> reIssueAccessToken(ReIssueRequest reIssueRequest) {
        Member member = memberRepository
                .findByEmail(reIssueRequest.getEmail()).orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));
        jwtProvider.checkRefreshToken(reIssueRequest.getEmail(), reIssueRequest.getRefreshToken());
        String accessToken = jwtProvider.createAccessToken(member.getName(), member.getRoles());
        return responseService.getSingleResult(LoginResponse.of(accessToken,reIssueRequest.getRefreshToken()));

    }

    public CommonResult logout(String email, String accessToken) {
        jwtProvider.logout(email, accessToken);
        return responseService.getSuccessResult();
    }
}
