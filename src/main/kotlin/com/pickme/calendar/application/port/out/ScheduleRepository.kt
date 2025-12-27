package com.pickme.calendar.application.port.out

import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec
import java.util.*

interface ScheduleRepository {
    fun findByScheduleId(scheduleId: String): Optional<InterviewSchedule>

    fun find(clientId: String, search: InterviewSearchSpec): List<InterviewSchedule>

    fun save(schedule: InterviewSchedule): InterviewSchedule

    fun deleteByScheduleId(scheduleId: String)
}
