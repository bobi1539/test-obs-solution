package com.test.obs.solution.constant;

import org.springframework.http.HttpStatus;

public enum GlobalMessage {
    SUCCESS(HttpStatus.OK, Constant.SUCCESS),
    DATA_NOT_EXIST(HttpStatus.BAD_REQUEST, Constant.DATA_NOT_EXIST),
    CANNOT_INSTANCE_HELPER_CLASS(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR);
    public final HttpStatus httpStatus;
    public final String message;

    GlobalMessage(org.springframework.http.HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
