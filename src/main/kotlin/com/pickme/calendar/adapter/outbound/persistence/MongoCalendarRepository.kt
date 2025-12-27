package com.pickme.calendar.adapter.outbound.persistence

import com.pickme.calendar.application.port.out.CalendarRepository
import com.pickme.calendar.domain.model.Calendar
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Primary
class MongoCalendarRepository(
    private val mongoRepo: SpringDataCalendarRepository
) : CalendarRepository {
    override fun findByClientId(clientId: String) = mongoRepo.findByClientId(clientId)

    override fun findByScheduleId(scheduleId: String) =
        mongoRepo.findBySchedules_id(scheduleId)

    override fun save(calendar: Calendar): Calendar = mongoRepo.save(calendar)
}

interface SpringDataCalendarRepository : MongoRepository<Calendar, String> {
    fun findByClientId(clientId: String): Optional<Calendar>

    fun findBySchedules_id(scheduleId: String): Optional<Calendar>
}
