package com.pickme.calendar.adapter.inbound.web.dto.request

import io.swagger.v3.oas.annotations.Parameter

data class SearchQueryDto(
    @field:Parameter(description = "회사 이름", example = "앙떼띠")
    val companyName: String?,

    @field:Parameter(description = "회사 장소", example = "잠실")
    val companyLocation: String?,

    @field:Parameter(description = "직무", example = "바리스타")
    val position: String?,

    @field:Parameter(description = "유형", example = "1차 기술면접")
    val category: String?
)
