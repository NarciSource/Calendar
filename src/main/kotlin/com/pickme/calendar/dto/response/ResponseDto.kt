package com.pickme.calendar.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "응답")
data class ResponseDto(
    @field:Schema(description = "성공 유무", example = "false")
    val success: Boolean,

    @field:Schema(description = "응답 메시지", example = "interviewDetailId에 해당하는 면접 일정이 없습니다.")
    val message: String?,

    @field:Schema(description = "면접 일정 ID", example = "fd3c55f5-07fe-4d0c-94d3-69cbaeb2646c")
    val interviewDetailId: String?,
)
