package com.pickme.calendar.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException exception) {
		return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
			.body(exception.getErrorCode().name() + ": " + exception.getMessage());
	}
}
