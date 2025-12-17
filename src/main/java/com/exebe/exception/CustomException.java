package com.exebe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final int code;

    private final HttpStatus status;

    public CustomException(int code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
