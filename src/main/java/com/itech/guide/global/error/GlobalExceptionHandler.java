package com.itech.guide.global.error;

import com.itech.guide.global.common.response.CommonResult;
import com.itech.guide.global.common.response.ResponseCode;
import com.itech.guide.global.common.response.ResponseService;
import com.itech.guide.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult handleException(Exception e) {
        return responseService.getFailResult(999,e.getMessage());
    }


}
