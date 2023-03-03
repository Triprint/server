package com.triprint.backend.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
		ErrorResponse response = new ErrorResponse(
				new Date(),
				ex.getMessage());

		return new ResponseEntity<>(response, ex.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException ex){
		ValidationErrorException validationErrorException = new ValidationErrorException();
		ErrorResponse errorResponse = validationErrorException.makeValidationErrorException(ex.getBindingResult());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MultipartException.class)
	ResponseEntity<ErrorResponse> handleMultipartException(MultipartException ex) throws IOException {
		ErrorResponse response = new ErrorResponse(
				new Date(),
				ex.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) throws IOException {
		ErrorResponse response = new ErrorResponse(
				new Date(),
				ex.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex) {
		ErrorResponse message = new ErrorResponse(
				new Date(),
				ex.getMessage());

		return new ResponseEntity<ErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
