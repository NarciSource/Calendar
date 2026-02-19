package com.pickme.schedule.application.port.out

import com.pickme.schedule.domain.model.InterviewSchedule
import com.pickme.schedule.domain.model.InterviewSearchSpec
import com.pickme.schedule.domain.model.InterviewUpdateSpec

interface ScheduleRepository {
    fun findByScheduleId(scheduleId: String, clientId: String): InterviewSchedule?

    fun findByScheduleIdIn(scheduleIds: List<String>): List<InterviewSchedule>

    fun find(search: InterviewSearchSpec, clientId: String): List<InterviewSchedule>

    fun save(schedule: InterviewSchedule): InterviewSchedule

    fun update(changes: InterviewUpdateSpec, scheduleId: String, clientId: String)

    fun deleteByScheduleId(scheduleId: String, clientId: String)
}
