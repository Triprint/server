package com.triprint.backend.domain.auth.security.oauth2.exception;

import com.triprint.backend.core.exception.ServiceException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotExpiredTokenYet extends ServiceException {
    public NotExpiredTokenYet(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
