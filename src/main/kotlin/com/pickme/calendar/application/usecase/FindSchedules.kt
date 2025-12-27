package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.CalendarRepository
import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewSchedule
import java.time.YearMonth
import java.util.function.Supplier

// 해당 사용자의 필터 조건에 맞는 면접 일정 조회
@UseCase
class FindSchedulesUseCase(
    private val repository: CalendarRepository
) {
    fun execute(query: FindSchedulesQuery): FindScheduleResult {

        val calendar = repository
            .findByClientId(query.clientId)
            .orElseThrow<CustomException>(Supplier { CustomException(ErrorCode.DOCUMENT_NOT_FOUND) })

        val schedules = calendar.schedules.stream() // 면접 일정 리스트를 스트림으로 변환
            .filter { interview ->
                query.name?.let { interview.isFromCompany(it) } ?: true
            }
            .filter { interview ->
                query.yearMonth?.let { interview.isInYearMonth(it) } ?: true
            }
            .toList() // 결과를 리스트로 반환

        return FindScheduleResult(calendar, schedules)
    }
}

data class FindSchedulesQuery(
    val clientId: String,
    val name: String?,
    val yearMonth: YearMonth?
)

data class FindScheduleResult(
    val calendar: Calendar,
    val schedules: List<InterviewSchedule>
)