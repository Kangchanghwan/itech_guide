package com.itech.guide.global.error.controller;

import com.itech.guide.global.common.response.CommonResult;
import com.itech.guide.global.error.exception.CAuthenticationEntryPointException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {


    @GetMapping(value = "/entrypoint")
    public ResponseEntity<CommonResult> entrypointException() {
        throw new CAuthenticationEntryPointException("해당 리소스에 접근하기 위한 권한이 없습니다.");
    }

    @GetMapping(value = "/accessdenied")
    public ResponseEntity<CommonResult> accessDeniedException()  {
        throw new AccessDeniedException("보유한 권한으로 접근할 수 없는 리소스 입니다.");
    }



}
