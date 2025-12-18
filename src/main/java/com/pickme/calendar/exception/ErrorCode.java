package com.pickme.calendar.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	NULL_USERINFO(HttpStatus.CONFLICT, ""),
	DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 면접 일정이 없습니다."),
	NOT_Bearer(HttpStatus.BAD_REQUEST, "Bearer 토큰 방식이 아닙니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
