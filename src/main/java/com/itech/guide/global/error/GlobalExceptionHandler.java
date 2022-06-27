package com.itech.guide.global.error;

import com.itech.guide.global.error.exception.BadRequestException;
import com.itech.guide.global.common.response.ResponseCode;
import com.itech.guide.global.common.response.ResponseDto;
import com.itech.guide.global.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        return responseService.send(ResponseCode.valueOf(e.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ResponseDto> handleClientRequestValidException(BindException exception){
        return responseService.send(ResponseCode.valueOf(
                exception.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getDefaultMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto> handleBadRequestException(BadRequestException e) {
        return  responseService.send(ResponseCode.valueOf(e.getMessage()));
    }
}
