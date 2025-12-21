package com.pickme.calendar.exception

import com.pickme.calendar.dto.response.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<*> {
        return ResponseEntity
            .status(exception.errorCode.httpStatus)
            .body<ResponseDto>(ResponseDto(false, exception.message, null))
    }
}
