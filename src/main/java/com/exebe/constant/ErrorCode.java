package com.exebe.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500),
    BAD_REQUEST(400),
    NOT_FOUND(404),


    INVALID_INPUT(1),
    ;

    private final int code;
    ErrorCode(int code) {
        this.code = code;
    }
}
