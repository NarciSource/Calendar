package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import java.time.YearMonth
import java.util.function.Supplier

@UseCase
class FindSchedulesUseCase(
    private val repository: ScheduleRepository
) {
    fun execute(query: FindSchedulesQuery): List<InterviewSchedule> {

        val schedules = repository.findByClientId(query.clientId)
            .orElseThrow<CustomException>(Supplier {
                CustomException(ErrorCode.DOCUMENT_NOT_FOUND)
            })
            .filter { interview ->
                query.name?.let { interview.isFromCompany(it) } ?: true
            }
            .filter { interview ->
                query.yearMonth?.let { interview.isInYearMonth(it) } ?: true
            }
            .toList()

        return schedules
    }
}

data class FindSchedulesQuery(
    val clientId: String,
    val name: String?,
    val yearMonth: YearMonth?
)
