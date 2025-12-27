package com.pickme.calendar.application.port.out

import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec
import com.pickme.calendar.domain.model.InterviewUpdateSpec
import java.util.*

interface ScheduleRepository {
    fun findByScheduleId(scheduleId: String): Optional<InterviewSchedule>

    fun find(clientId: String, search: InterviewSearchSpec): List<InterviewSchedule>

    fun save(schedule: InterviewSchedule): InterviewSchedule

    fun update(scheduleId: String, changes: InterviewUpdateSpec)

    fun deleteByScheduleId(scheduleId: String)
}
