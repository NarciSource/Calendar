package com.pickme.calendar.adapter.inbound.web.controller

import com.pickme.calendar.adapter.inbound.web.dto.request.PostInterviewDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PutInterviewDto
import com.pickme.calendar.adapter.inbound.web.dto.response.CalendarDto
import com.pickme.calendar.adapter.inbound.web.dto.response.ResponseDto
import com.pickme.calendar.application.usecase.InterviewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.YearMonth

@RestController
@RequestMapping("/calendar")
@Tag(name = "Calendar", description = "면접 캘린더 API")
@ApiResponse(responseCode = "400", description = "잘못된 요청")
@ApiResponse(responseCode = "401", description = "권한 없음")
@ApiResponse(responseCode = "404", description = "면접 일정이 없음")
class CalendarController(
    val interviewService: InterviewService
) {

    // 해당 사용자 면접 일정 전체 조회
    @Operation(summary = "면접 일정 조건 조회", description = "조건에 해당하는 면접 일정 조회")
    @ApiResponse(
        responseCode = "200",
        description = "조회 요청 성공",
        content = [Content(schema = Schema(implementation = CalendarDto::class))]
    )
    @GetMapping("/interviews")
    fun interviewsList(
        request: HttpServletRequest,
        @Parameter(description = "회사 이름 (필터링 조건)", example = "앙떼띠")
        @RequestParam(required = false)
        name: String?,
        @Parameter(description = "조회할 년/월 (yyyyMM 형식, 필터링 조건)", example = "2024-11")
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM")
        yearMonth: YearMonth?
    ): ResponseEntity<*> {
        val clientId = request.getAttribute("clientId") as String

        val calendarDto = interviewService.searchInterviews(clientId, name, yearMonth)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 조회 성공", calendarDto)
        )
    }

    // 해당 사용자의 interviewDetailId에 해당하는 면접 일정 조회
    @Operation(summary = "면접 일정 조회", description = "interviewDetailId에 해당하는 면접 일정 조회")
    @ApiResponse(
        responseCode = "200",
        description = "조회 요청 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @GetMapping("/interview")
    fun interview(
        @Parameter(description = "면접 일정 ID", example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34")
        @RequestParam interviewDetailId: String
    ): ResponseEntity<*> {
        val getInterviewDto = interviewService.getInterview(interviewDetailId)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 조회 성공", getInterviewDto)
        )
    }

    // 면접 일정 추가
    @Operation(summary = "면접 일정 추가", description = "새로운 면접 일정 추가")
    @ApiResponse(
        responseCode = "200",
        description = "면접 일정 추가 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @PostMapping("/interview")
    fun createInterviewSchedule(
        request: HttpServletRequest,
        @RequestBody postInterviewDto: PostInterviewDto
    ): ResponseEntity<*> {
        val clientId = request.getAttribute("clientId") as String

        val id = interviewService.registerInterviewSchedule(postInterviewDto, clientId)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 추가 성공", mapOf("id" to id))
        )
    }

    // 특정 면접 일정 수정
    @Operation(summary = "면접 일정 수정", description = "interviewDetailId에 해당하는 면접 일정 수정")
    @ApiResponse(
        responseCode = "200",
        description = "면접 일정 수정 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @PutMapping("/interview")
    fun putInterviewSchedule(
        @Parameter(
            description = "면접 일정 ID (필터링 조건)",
            example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34"
        )
        @RequestParam interviewDetailId: String,
        @RequestBody putInterviewDto: PutInterviewDto
    ): ResponseEntity<*> {
        val id = interviewService.putInterviewSchedule(interviewDetailId, putInterviewDto)

        return ResponseEntity.ok(
            ResponseDto(true, "면접 일정 수정 성공", data = mapOf("id" to id))
        )
    }

    // 면접 일정 삭제
    @Operation(summary = "면접 일정 삭제", description = "interviewDetailId에 해당하는 면접 일정 삭제")
    @ApiResponse(
        responseCode = "200",
        description = "면접 일정 삭제 성공",
        content = [Content(schema = Schema(implementation = ResponseDto::class))]
    )
    @DeleteMapping("/interview")
    fun deleteInterviewSchedule(
        @Parameter(
            description = "면접 일정 ID (필터링 조건)",
            example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34"
        )
        @RequestParam interviewDetailId: String
    ): ResponseEntity<*> {
        interviewService.deleteInterviewSchedule(interviewDetailId)

        return ResponseEntity.ok(
            ResponseDto<Nothing>(true, "면접 일정 삭제 성공")
        )
    }
}
