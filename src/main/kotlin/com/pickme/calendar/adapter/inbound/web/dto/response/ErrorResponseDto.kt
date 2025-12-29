package com.pickme.calendar.adapter.inbound.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 에러 응답")
data class ErrorResponseDto<T>(

    @field:Schema(description = "성공 유무", example = "false")
    override val success: Boolean = false,

    @field:Schema(description = "응답 메시지", example = "에러 문구")
    override val message: String? = null,

    @field:Schema(description = "반환 데이터")
    override val data: T? = null

) : ResponseDto<T>(success)
