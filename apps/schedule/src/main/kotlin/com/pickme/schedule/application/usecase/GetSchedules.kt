package com.pickme.schedule.application.usecase

import com.pickme.schedule.application.annotation.UseCase
import com.pickme.schedule.application.port.out.ScheduleRepository
import com.pickme.schedule.domain.model.InterviewSchedule

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