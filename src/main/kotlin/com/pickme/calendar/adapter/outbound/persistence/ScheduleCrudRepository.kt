package com.pickme.calendar.adapter.outbound.persistence

import com.pickme.calendar.domain.model.InterviewSchedule
import org.springframework.data.mongodb.repository.MongoRepository

interface ScheduleCrudRepository : MongoRepository<InterviewSchedule, String> {
    fun findByIdAndClientId(id: String, clientId: String): InterviewSchedule?

    fun findByIdIn(ids: Collection<String>): List<InterviewSchedule>

    fun deleteByIdAndClientId(id: String, clientId: String)
}
