package com.itech.guide.global.error;

import com.itech.guide.global.common.response.CommonResult;
import com.itech.guide.global.common.response.ResponseCode;
import com.itech.guide.global.common.response.ResponseService;
import com.itech.guide.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonResult> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.getFailResult(ResponseCode.F_UNKNOWN_ERR));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<CommonResult> handleClientRequestValidException(BindException exception){
//        String messages = exception.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(e -> "["+ e.getCode() + "] "+ e.getDefaultMessage())
//                .collect(Collectors.toList()).toString();

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.getFailResult(ResponseCode.F_VALIDATION));

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResult> handleBadRequestException(BadRequestException e) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.getFailResult(ResponseCode.F_UNKNOWN_ERR));
    }
}
