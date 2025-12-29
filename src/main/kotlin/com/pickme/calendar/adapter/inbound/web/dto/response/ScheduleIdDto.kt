package com.pickme.calendar.adapter.inbound.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "일정 아이디")
data class ScheduleIdDto(

    @field:Schema(description = "일정 ID", example = "694d1d9462a47e4039250532")
    val id: String
)
