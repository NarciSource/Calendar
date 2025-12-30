package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule

@UseCase
class GetSchedules(
    private val repository: ScheduleRepository
) {
    fun execute(query: GetSchedulesQuery): List<InterviewSchedule> {

        val schedules = repository.findByScheduleIdIn(query.scheduleIds)
        return schedules
    }
}

data class GetSchedulesQuery(
    val scheduleIds: List<String>
)