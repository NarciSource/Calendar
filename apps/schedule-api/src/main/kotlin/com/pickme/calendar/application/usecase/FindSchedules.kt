package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec

@UseCase
class FindSchedulesUseCase(
    private val repository: ScheduleRepository,
) {
    fun execute(query: FindSchedulesQuery): List<InterviewSchedule> {

        val schedules = repository.find(query.search, query.clientId)
        return schedules
    }
}

data class FindSchedulesQuery(
    val search: InterviewSearchSpec,
    val clientId: String,
)
