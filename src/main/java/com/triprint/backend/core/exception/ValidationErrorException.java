package com.triprint.backend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.Date;

@Getter
public class ValidationErrorException {
    public ErrorResponse makeValidationErrorException(BindingResult bindingResult){
        Date timestamp = new Date();
        String message = "";

        if (bindingResult.hasErrors()){
            message = bindingResult.getFieldError().getDefaultMessage();
        }
        return new ErrorResponse(timestamp, message);
    }
}
