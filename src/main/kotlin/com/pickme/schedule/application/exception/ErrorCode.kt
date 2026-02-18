package com.pickme.schedule.application.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String,
) {
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 면접 일정이 없습니다."),
}
