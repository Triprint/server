package com.triprint.backend.domain.auth.security.oauth2.exception;

import com.triprint.backend.core.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class TokenValidFailedException extends ServiceException {

    public TokenValidFailedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}