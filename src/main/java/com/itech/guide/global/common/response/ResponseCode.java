package com.itech.guide.global.common.response;


public enum ResponseCode {

    F_TOKEN(300, "Token Fail"),
    F_ORG_CD(301, "Org Fail"),
    F_NAME(302, "Name Fail"),
    F_UID(303, "Uid Fail"),
    F_SIGNUP(304,"Sign up Fail"),
    F_VALIDATION(305,"Validation failed."),
    F_EMPTY_ACC(400, "No Remaining Accounts"),
    F_UNKNOWN_ERR(999,"unknown error");
    public final int messageCode;
    public final String message;

    ResponseCode(int messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }
}
