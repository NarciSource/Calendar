package com.pickme.calendar.application.port.out

import com.pickme.calendar.domain.model.Calendar
import java.util.*

interface CalendarRepository {
    fun findByClientId(clientId: String): Optional<Calendar>

    fun findByInterviewId(interviewDetailId: String): Optional<Calendar>

    fun save(calendar: Calendar): Calendar
}
