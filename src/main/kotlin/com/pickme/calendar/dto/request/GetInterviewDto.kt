package com.pickme.calendar.dto.request

import com.pickme.calendar.dto.request.payload.CompanyDto
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(description = "면접 일정 조회")
data class GetInterviewDto(
    @Schema(description = "면접 일정 ID", example = "fd3c55f5-07fe-4d0c-94d3-69cbaeb2646c")
    val interviewDetailId: String,

    @Schema(description = "생성 시간", example = "2025-03-05T13:43:35.901")
    val createdAt: LocalDateTime,

    @Schema(description = "수정 시간", example = "2025-03-05T13:43:35.901")
    val updatedAt: LocalDateTime,

    val company: CompanyDto,

    @Schema(description = "면접 일정 시간", example = "2024-12-01T02:49:03.465+00:00")
    val interviewTime: Date,

    @Schema(description = "직무", example = "바리스타")
    val position: String,

    @Schema(description = "면접 유형", example = "1차 기술면접")
    val category: String,

    @Schema(description = "추가 사항", example = "면접 전 15분 전까지 도착하기")
    val description: String,
)
