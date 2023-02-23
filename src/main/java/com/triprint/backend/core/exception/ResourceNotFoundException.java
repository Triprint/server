package com.triprint.backend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends ServiceException{

    public ResourceNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
