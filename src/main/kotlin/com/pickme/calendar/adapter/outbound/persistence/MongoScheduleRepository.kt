package com.pickme.calendar.adapter.outbound.persistence

import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Primary
class MongoScheduleRepository(
    private val mongoRepo: SpringDataScheduleRepository
) : ScheduleRepository {
    override fun findByClientId(clientId: String) = mongoRepo.findByClientId(clientId)

    override fun findByScheduleId(scheduleId: String): Optional<InterviewSchedule> = mongoRepo.findById(scheduleId)

    override fun save(schedule: InterviewSchedule): InterviewSchedule = mongoRepo.save(schedule)

    override fun deleteByScheduleId(scheduleId: String) = mongoRepo.deleteById(scheduleId)
}

interface SpringDataScheduleRepository : MongoRepository<InterviewSchedule, String> {
    fun findByClientId(clientId: String): Optional<List<InterviewSchedule>>
}
