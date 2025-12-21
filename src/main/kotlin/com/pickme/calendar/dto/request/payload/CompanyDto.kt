package com.pickme.calendar.dto.request.payload

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회사 정보")
data class CompanyDto(
    @field:Schema(description = "회사명", example = "앙떼띠")
    val name: String,

    @field:Schema(description = "회사 위치", example = "잠실")
    val location: String
)
