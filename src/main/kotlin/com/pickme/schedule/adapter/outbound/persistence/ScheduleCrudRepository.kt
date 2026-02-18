package com.pickme.schedule.adapter.outbound.persistence

import com.pickme.schedule.domain.model.InterviewSchedule
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository


@Profile("!openapi")
interface ScheduleCrudRepository : MongoRepository<InterviewSchedule, String> {
    fun findByIdAndClientId(id: String, clientId: String): InterviewSchedule?

    fun findByIdIn(ids: Collection<String>): List<InterviewSchedule>

    fun deleteByIdAndClientId(id: String, clientId: String)
}
