package com.pickme.schedule.adapter.inbound.web.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.pickme.schedule.adapter.inbound.web.dto.request.payload.CompanyDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

@Schema(description = "면접 일정 추가")
data class PostScheduleDto(
    @field:NotNull
    val company: CompanyDto,

    @field:Schema(description = "면접 일정 시간", example = "2024-12-01T02:49:03.465+00:00")
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @field:NotNull
    @field:Future
    val date: Date,

    @field:Schema(description = "직무", example = "바리스타")
    @field:NotBlank
    val position: String,

    @field:Schema(description = "면접 유형", example = "1차 기술면접")
    @field:NotBlank
    val category: String,

    @field:Schema(description = "추가 사항", example = "면접 전 15분 전까지 도착하기")
    val description: String
)
