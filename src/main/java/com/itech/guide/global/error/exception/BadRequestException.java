package com.itech.guide.global.error.exception;

public class BadRequestException  extends   RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
