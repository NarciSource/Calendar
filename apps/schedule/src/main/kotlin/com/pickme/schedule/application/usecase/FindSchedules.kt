package com.pickme.schedule.application.usecase

import com.pickme.schedule.application.annotation.UseCase
import com.pickme.schedule.application.port.out.ScheduleRepository
import com.pickme.schedule.domain.model.InterviewSchedule
import com.pickme.schedule.domain.model.InterviewSearchSpec

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
