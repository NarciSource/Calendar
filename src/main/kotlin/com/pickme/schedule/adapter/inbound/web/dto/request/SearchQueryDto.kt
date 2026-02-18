package com.pickme.schedule.adapter.inbound.web.dto.request

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class SearchQueryDto(
    @field:Parameter(description = "회사 이름", example = "앙떼띠")
    val companyName: String?,

    @field:Parameter(description = "회사 장소", example = "잠실")
    val companyLocation: String?,

    @field:Parameter(description = "직무", example = "바리스타")
    val position: String?,

    @field:Parameter(description = "유형", example = "1차 기술면접")
    val category: String?,

    @field:Parameter(description = "시작 날짜", example = "2024-12-01")
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val from: LocalDate?,

    @field:Parameter(description = "종료 날짜", example = "2024-12-31")
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val to: LocalDate?
)
