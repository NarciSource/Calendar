package com.pickme.calendar.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pickme.calendar.dto.response.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException exception) {
		return ResponseEntity
			.status(exception.getErrorCode().getHttpStatus())
			.body(new ResponseDto(false, exception.getMessage(), null));
	}
}
