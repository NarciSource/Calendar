package com.pickme.calendar.adapter.inbound.web.controller

import com.pickme.calendar.adapter.inbound.web.dto.response.ErrorResponseDto
import com.pickme.calendar.adapter.inbound.web.dto.response.ResponseDto
import com.pickme.calendar.adapter.inbound.web.dto.response.ScheduleDto
import com.pickme.calendar.adapter.inbound.web.mapper.InterviewScheduleMapper
import com.pickme.calendar.application.usecase.GetSchedules
import com.pickme.calendar.application.usecase.GetSchedulesQuery
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal")
@ApiResponse(
    responseCode = "400", description = "잘못된 요청",
    content = [Content(schema = Schema(implementation = ErrorResponseDto::class))]
)
@ApiResponse(
    responseCode = "404", description = "면접 일정이 없음",
    content = [Content(schema = Schema(implementation = ErrorResponseDto::class))]
)
@Tag(name = "Internal", description = "내부 호출 전용")
class InternalController(
    private val getSchedules: GetSchedules,
    private val scheduleMapper: InterviewScheduleMapper
) {

    @Operation(summary = "일정 모음 조회", description = "scheduleId들에 해당하는 면접 일정 조회")
    @ApiResponse(responseCode = "200", description = "조회 요청 성공")
    @GetMapping("/schedules")
    fun getSchedules(
        @Parameter(description = "면접 일정 ID", example = "[\"695379e58f03547bad0bc4c1\",\"695379e38f03547bad0bc4c2\"]")
        @RequestParam scheduleIds: List<String>
    ): ResponseEntity<ResponseDto<List<ScheduleDto>>> {

        val found = getSchedules.execute(
            GetSchedulesQuery(scheduleIds)
        )

        val scheduleDto = scheduleMapper.toDto(found)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 조회 성공", scheduleDto)
        )
    }
}