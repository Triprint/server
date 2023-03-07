package com.triprint.backend.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ServiceException {

	public ResourceNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
