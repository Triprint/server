package com.triprint.backend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends ServiceException{
    public ForbiddenException(String message){
        super(HttpStatus.FORBIDDEN, message);
    }
}
