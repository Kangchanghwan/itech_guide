package com.itech.guide.global.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.itech.guide.global.util.CustomLocalDateTimeSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ResponseService {

    public ResponseEntity<ResponseDto> send(ResponseCode responseCode) {
        return new ResponseEntity<>(setBasicResponse(responseCode), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> sendData(ResponseCode responseCode, Object data) {
        ResponseDto basicResponse = setBasicResponse(responseCode);
        basicResponse.addData(data);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    public String sendTextOfJson(ResponseCode status) throws JsonProcessingException {
        ResponseDto resDto = ResponseDto
                .builder()
                .responseCode(status)
                .build();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);

        return objectMapper.writeValueAsString(resDto);
    }

    private ResponseDto setBasicResponse(ResponseCode responseCode) {
        return ResponseDto
                .builder()
                .responseCode(responseCode)
                .build();
    }

}
