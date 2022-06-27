package com.itech.guide.global.common.response;

public enum ResponseCode {

    S_OK(200, "success"),
    F_TOKEN(300, "Token Fail"),
    F_ORG_CD(301, "Org Fail"),
    F_NAME(302, "Name Fail"),
    F_UID(303, "Uid Fail"),
    F_SIGNUP(304,"sign up Fail"),
    F_EPTY_ACC(400, "No Remaining Accounts");

    public final int messageCode;
    public final String message;
    ResponseCode(int messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }
}
