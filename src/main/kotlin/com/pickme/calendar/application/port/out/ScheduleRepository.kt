package com.pickme.calendar.application.port.out

import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec
import com.pickme.calendar.domain.model.InterviewUpdateSpec
import java.util.*

interface ScheduleRepository {
    fun findByScheduleId(scheduleId: String, clientId: String): Optional<InterviewSchedule>

    fun find(search: InterviewSearchSpec, clientId: String): List<InterviewSchedule>

    fun save(schedule: InterviewSchedule): InterviewSchedule

    fun update(changes: InterviewUpdateSpec, scheduleId: String, clientId: String)

    fun deleteByScheduleId(scheduleId: String, clientId: String)
}
