package com.pickme.calendar.adapter.inbound.web.controller

import com.pickme.calendar.adapter.inbound.web.api.ApiPaths
import com.pickme.calendar.adapter.inbound.web.dto.request.PostScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PutScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.SearchQueryDto
import com.pickme.calendar.adapter.inbound.web.dto.response.ResponseDto
import com.pickme.calendar.adapter.inbound.web.dto.response.ScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.response.ScheduleIdDto
import com.pickme.calendar.adapter.inbound.web.mapper.InterviewScheduleMapper
import com.pickme.calendar.application.usecase.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ApiPaths.V2)
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

    @Operation(summary = "면접 일정 조회", description = "scheduleId에 해당하는 면접 일정 조회")
    @ApiResponse(responseCode = "200", description = "조회 요청 성공")
    @GetMapping("/schedule")
    fun getSchedule(
        request: HttpServletRequest,
        @Parameter(description = "면접 일정 ID", example = "694d1d9462a47e4039250532")
        @RequestParam scheduleId: String
    ): ResponseEntity<ResponseDto<ScheduleDto>> {
        val clientId = request.getAttribute("clientId") as String

        val schedule = getSchedule.execute(
            GetScheduleQuery(scheduleId, clientId)
        )

        val scheduleDto = scheduleMapper.toDto(schedule)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 조회 성공", scheduleDto)
        )
    }

    @Operation(summary = "면접 일정 조건 조회", description = "조건에 해당하는 면접 일정 조회")
    @ApiResponse(responseCode = "200", description = "조회 요청 성공")
    @GetMapping("/schedules")
    fun searchSchedules(
        request: HttpServletRequest,
        @ParameterObject searchQueryDto: SearchQueryDto,
    ): ResponseEntity<ResponseDto<List<ScheduleDto>>> {
        val clientId = request.getAttribute("clientId") as String
        val search = scheduleMapper.toEntity(searchQueryDto)

        val found = findSchedules.execute(
            FindSchedulesQuery(search, clientId)
        )

        val scheduleDto = scheduleMapper.toDto(found)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 조회 성공", scheduleDto)
        )
    }

    @Operation(summary = "면접 일정 추가", description = "새로운 면접 일정 추가")
    @ApiResponse(responseCode = "200", description = "면접 일정 추가 성공")
    @PostMapping("/schedule")
    fun createSchedule(
        request: HttpServletRequest,
        @Valid @RequestBody postScheduleDto: PostScheduleDto
    ): ResponseEntity<ResponseDto<ScheduleIdDto>> {
        val clientId = request.getAttribute("clientId") as String

        val schedule = scheduleMapper.toEntity(postScheduleDto, clientId)

        val scheduleId = registerSchedule.execute(
            RegisterScheduleCommand(clientId, schedule)
        )

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 추가 성공", ScheduleIdDto(scheduleId))
        )
    }

    @Operation(summary = "면접 일정 수정", description = "scheduleId에 해당하는 면접 일정 수정")
    @ApiResponse(responseCode = "200", description = "면접 일정 수정 성공")
    @PutMapping("/schedule")
    fun updateSchedule(
        request: HttpServletRequest,
        @Parameter(description = "면접 일정 ID", example = "694d1d9462a47e4039250532")
        @RequestParam scheduleId: String,
        @Valid @RequestBody putScheduleDto: PutScheduleDto
    ): ResponseEntity<ResponseDto<Nothing>> {
        val clientId = request.getAttribute("clientId") as String
        val changes = scheduleMapper.toEntity(putScheduleDto)

        updateSchedule.execute(
            UpdateScheduleCommand(changes, scheduleId, clientId)
        )

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 수정 성공")
        )
    }

    // 면접 일정 삭제
    @Operation(summary = "면접 일정 삭제", description = "scheduleId에 해당하는 면접 일정 삭제")
    @ApiResponse(responseCode = "200", description = "면접 일정 삭제 성공")
    @DeleteMapping("/schedule")
    fun deleteSchedule(
        request: HttpServletRequest,
        @Parameter(description = "면접 일정 ID", example = "694d1d9462a47e4039250532")
        @RequestParam scheduleId: String
    ): ResponseEntity<ResponseDto<Nothing>> {
        val clientId = request.getAttribute("clientId") as String

        deleteSchedule.execute(
            DeleteScheduleCommand(scheduleId, clientId)
        )

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 삭제 성공")
        )
    }
}
