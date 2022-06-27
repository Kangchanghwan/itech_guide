package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.dto.LoginResponse;
import com.itech.guide.domain.member.entity.Member;
import com.itech.guide.global.error.exception.BadRequestException;
import com.itech.guide.domain.member.repository.MemberRepository;
import com.itech.guide.global.common.response.ResponseCode;
import com.itech.guide.global.common.response.ResponseDto;
import com.itech.guide.global.common.response.ResponseService;
import com.itech.guide.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<ResponseDto> login(String name, String password) {
        Member member = memberRepository
                .findByName(name).orElseThrow(() -> new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));
        checkPassword(password, member.getPassword());
        String accessToken = jwtProvider.createAccessToken(member.getName(), member.getRole());
        String refreshToken = jwtProvider.createRefreshToken(member.getName(), member.getRole());

        return responseService.sendData(ResponseCode.S_OK,LoginResponse.of(accessToken,refreshToken));
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");
        }
    }

    public ResponseEntity<ResponseDto> reIssueAccessToken(String name, String refreshToken) {
        Member member = memberRepository
                .findByName(name).orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));
        jwtProvider.checkRefreshToken(name, refreshToken);
        String accessToken = jwtProvider.createAccessToken(member.getName(), member.getRole());

        return responseService.sendData(ResponseCode.S_OK,LoginResponse.of(accessToken,refreshToken));

    }

    public ResponseEntity<ResponseDto> logout(String email, String accessToken) {
        jwtProvider.logout(email, accessToken);
        return responseService.sendData(ResponseCode.S_OK,"로그아웃 완료");
    }
}
