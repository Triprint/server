package com.triprint.backend.core.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorResponse {
    private Date timestamp;
    private String message;

    public ErrorResponse(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}
