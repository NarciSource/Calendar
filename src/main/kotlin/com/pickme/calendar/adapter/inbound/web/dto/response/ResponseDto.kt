package com.pickme.calendar.adapter.inbound.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 응답")
open class ResponseDto<T>(

    @field:Schema(description = "성공 유무", example = "true")
    open val success: Boolean,

    @field:Schema(description = "응답 메시지", example = "면접 일정 작업")
    open val message: String? = null,

    @field:Schema(description = "반환 데이터")
    open val data: T? = null,
)
