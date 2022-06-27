package com.itech.guide.global.common.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto {


    private int message_code;

    private String message;

    private Object data;

    @Builder
    private ResponseDto(ResponseCode responseCode){
        this.message_code = responseCode.messageCode;
        this.message = responseCode.message;
    }

    public void addData(Object data) {
        this.data = data;
    }


}
