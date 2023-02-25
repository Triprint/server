package com.triprint.backend.domain.auth.security.oauth2.exception;

import com.triprint.backend.core.exception.ServiceException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotExpiredTokenYetException extends ServiceException {
    public NotExpiredTokenYetException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
