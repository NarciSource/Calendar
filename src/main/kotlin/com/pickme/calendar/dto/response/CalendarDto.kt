package com.pickme.calendar.dto.response

import com.pickme.calendar.dto.request.GetInterviewDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "전체 조회 & 조건 조회 시 응답")
data class CalendarDto(
    @field:Schema(description = "사용자 ID", example = "60011bt0ev46lpb51pbnpugpn7")
    val clientId: String,

    @field:Schema(description = "면접 일정 목록")
    var interviewDetails: List<GetInterviewDto>,
)
