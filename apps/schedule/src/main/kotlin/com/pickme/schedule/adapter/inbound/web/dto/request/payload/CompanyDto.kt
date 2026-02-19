package com.pickme.schedule.adapter.inbound.web.dto.request.payload

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "회사 정보")
data class CompanyDto(
    @field:Schema(description = "회사명", example = "앙떼띠")
    @field:NotBlank
    val name: String,

    @field:Schema(description = "회사 위치", example = "잠실")
    val location: String
)
