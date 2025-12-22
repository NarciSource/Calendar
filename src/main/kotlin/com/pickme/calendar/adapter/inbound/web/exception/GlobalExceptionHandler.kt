package com.pickme.calendar.adapter.inbound.web.exception

import com.pickme.calendar.adapter.inbound.web.dto.response.ResponseDto
import com.pickme.calendar.application.exception.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<*> {
        return ResponseEntity
            .status(exception.errorCode.httpStatus)
            .body(
                ResponseDto(false, exception.message, null)
            )
    }
}