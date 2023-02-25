package com.triprint.backend.core.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private String description;

    public ErrorResponse(Date timestamp, String message, String description) {
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }
}
