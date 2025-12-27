package com.pickme.calendar.application.port.out

import com.pickme.calendar.domain.model.InterviewSchedule
import java.util.*

interface ScheduleRepository {
    fun findByClientId(clientId: String): Optional<List<InterviewSchedule>>

    fun findByScheduleId(scheduleId: String): Optional<InterviewSchedule>

    fun save(schedule: InterviewSchedule): InterviewSchedule

    fun deleteByScheduleId(scheduleId: String)
}
