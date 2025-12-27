package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec
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
            .filter { it.matches(query.search) }
            .toList()

        return schedules
    }
}

data class FindSchedulesQuery(
    val clientId: String,
    val search: InterviewSearchSpec
)
