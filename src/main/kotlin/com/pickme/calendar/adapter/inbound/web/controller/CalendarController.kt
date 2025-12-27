package com.pickme.calendar.adapter.inbound.web.controller

import com.pickme.calendar.adapter.inbound.web.dto.request.GetScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PostScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PutScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.SearchQueryDto
import com.pickme.calendar.adapter.inbound.web.dto.response.ResponseDto
import com.pickme.calendar.adapter.inbound.web.mapper.InterviewScheduleMapper
import com.pickme.calendar.application.usecase.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/calendar")
@Tag(name = "Calendar", description = "면접 캘린더 API")
@ApiResponse(responseCode = "400", description = "잘못된 요청")
@ApiResponse(responseCode = "401", description = "권한 없음")
@ApiResponse(responseCode = "404", description = "면접 일정이 없음")
class CalendarController(
    private val findSchedules: FindSchedulesUseCase,
    private val getSchedule: GetScheduleUseCase,
    private val registerSchedule: RegisterScheduleUseCase,
    private val updateSchedule: UpdateScheduleUseCase,
    private val deleteSchedule: DeleteScheduleUseCase,
    private val scheduleMapper: InterviewScheduleMapper
) {

    @Operation(summary = "면접 일정 조건 조회", description = "조건에 해당하는 면접 일정 조회")
    @ApiResponse(
        responseCode = "200",
        description = "조회 요청 성공",
        content = [Content(schema = Schema(implementation = GetScheduleDto::class))]
    )
    @GetMapping("/interviews")
    fun searchInterviews(
        request: HttpServletRequest,
        @ParameterObject
        searchQueryDto: SearchQueryDto,
    ): ResponseEntity<*> {
        val clientId = request.getAttribute("clientId") as String
        val search = scheduleMapper.toEntity(searchQueryDto)

        val found = findSchedules.execute(
            FindSchedulesQuery(clientId, search)
        )

        val scheduleDto = scheduleMapper.toDto(found)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 조회 성공", scheduleDto)
        )
    }

    @Operation(summary = "면접 일정 조회", description = "scheduleId에 해당하는 면접 일정 조회")
    @ApiResponse(
        responseCode = "200",
        description = "조회 요청 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @GetMapping("/interview")
    fun getInterview(
        @Parameter(description = "면접 일정 ID", example = "694d1d9462a47e4039250532")
        @RequestParam scheduleId: String
    ): ResponseEntity<*> {

        val schedule = getSchedule.execute(
            GetScheduleQuery(scheduleId)
        )

        val scheduleDto = scheduleMapper.toDto(schedule)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 조회 성공", scheduleDto)
        )
    }

    @Operation(summary = "면접 일정 추가", description = "새로운 면접 일정 추가")
    @ApiResponse(
        responseCode = "200",
        description = "면접 일정 추가 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @PostMapping("/interview")
    fun createInterview(
        request: HttpServletRequest,
        @RequestBody postScheduleDto: PostScheduleDto
    ): ResponseEntity<*> {
        val clientId = request.getAttribute("clientId") as String

        val schedule = scheduleMapper.toEntity(postScheduleDto, clientId)

        val scheduleId = registerSchedule.execute(
            RegisterScheduleCommand(clientId, schedule)
        )

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 추가 성공", mapOf("scheduleId" to scheduleId))
        )
    }

    @Operation(summary = "면접 일정 수정", description = "scheduleId에 해당하는 면접 일정 수정")
    @ApiResponse(
        responseCode = "200",
        description = "면접 일정 수정 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @PutMapping("/interview")
    fun updateInterview(
        @Parameter(description = "면접 일정 ID", example = "694d1d9462a47e4039250532")
        @RequestParam scheduleId: String,
        @RequestBody putScheduleDto: PutScheduleDto
    ): ResponseEntity<*> {
        val changes = scheduleMapper.toEntity(putScheduleDto)

        updateSchedule.execute(
            UpdateScheduleCommand(scheduleId, changes)
        )

        return ResponseEntity.ok(
            ResponseDto<Nothing>(true, "면접 일정 수정 성공")
        )
    }

    // 면접 일정 삭제
    @Operation(summary = "면접 일정 삭제", description = "scheduleId에 해당하는 면접 일정 삭제")
    @ApiResponse(
        responseCode = "200",
        description = "면접 일정 삭제 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @DeleteMapping("/interview")
    fun deleteInterview(
        @Parameter(description = "면접 일정 ID", example = "694d1d9462a47e4039250532")
        @RequestParam scheduleId: String
    ): ResponseEntity<*> {
        deleteSchedule.execute(
            DeleteScheduleCommand(scheduleId)
        )

        return ResponseEntity.ok(
            ResponseDto<Nothing>(true, "면접 일정 삭제 성공")
        )
    }
}
