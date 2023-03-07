package com.triprint.backend.core.exception;

import java.util.Date;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class ValidationErrorException {
	public ErrorResponse makeValidationErrorException(BindingResult bindingResult) {
		Date timestamp = new Date();
		String message = "";

		if (bindingResult.hasErrors()) {
			message = bindingResult.getFieldError().getDefaultMessage();
		}
		return new ErrorResponse(timestamp, message);
	}
}
