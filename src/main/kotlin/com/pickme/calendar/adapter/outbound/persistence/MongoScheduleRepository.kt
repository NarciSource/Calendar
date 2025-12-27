package com.pickme.calendar.adapter.outbound.persistence

import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Primary
class MongoScheduleRepository(
    private val mongoRepo: SpringDataScheduleRepository,
    private val mongoTemplate: MongoTemplate
) : ScheduleRepository {

    override fun findByClientId(clientId: String) = mongoRepo.findByClientId(clientId)

    override fun findByScheduleId(scheduleId: String): Optional<InterviewSchedule> = mongoRepo.findById(scheduleId)

    override fun find(clientId: String, search: InterviewSearchSpec): List<InterviewSchedule> {
        val query = Query()
            .andIfNotNull(
                clientId.takeIf { it.isNotBlank() }
                    ?.let { Criteria.where("clientId").`is`(it) }
            )
            .andIfNotNull(
                search.companyName?.takeIf { it.isNotBlank() }
                    ?.let { Criteria.where("company.name").`is`(it) }
            )
            .andIfNotNull(
                search.companyLocation?.takeIf { it.isNotBlank() }
                    ?.let { Criteria.where("company.location").`is`(it) }
            )
            .andIfNotNull(
                search.position?.takeIf { it.isNotBlank() }
                    ?.let { Criteria.where("position").`is`(it) }
            )
            .andIfNotNull(
                search.category?.takeIf { it.isNotBlank() }
                    ?.let { Criteria.where("category").`is`(it) }
            )
            .with(Sort.by("date"))

        val schedules = mongoTemplate.find<InterviewSchedule>(query)

        return schedules
    }

    override fun save(schedule: InterviewSchedule): InterviewSchedule = mongoRepo.save(schedule)

    override fun deleteByScheduleId(scheduleId: String) = mongoRepo.deleteById(scheduleId)


}

interface SpringDataScheduleRepository : MongoRepository<InterviewSchedule, String> {
    fun findByClientId(clientId: String): Optional<List<InterviewSchedule>>
}

fun Query.andIfNotNull(criteria: Criteria?) = apply {
    criteria?.let { addCriteria(it) }
}