package com.pickme.calendar.adapter.inbound.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 응답")
data class ResponseDto<T>(
    @field:Schema(description = "성공 유무", example = "true")
    val success: Boolean,

    @field:Schema(description = "응답 메시지", example = "면접 일정 작업")
    val message: String? = null,

    @field:Schema(description = "반환 데이터")
    val data: T? = null,
)
