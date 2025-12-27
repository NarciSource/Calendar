package com.pickme.calendar.adapter.outbound.persistence

import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec
import com.pickme.calendar.domain.model.InterviewUpdateSpec
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.updateFirst
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.time.Clock

@Repository
@Primary
class MongoScheduleRepository(
    private val mongoRepo: SpringDataScheduleRepository,
    private val mongoTemplate: MongoTemplate
) : ScheduleRepository {

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
            .andIfNotNull(
                listOf(search.from, search.to).any { it != null }.takeIf { it }
                    ?.let {
                        Criteria.where("date").apply {
                            search.from?.let { gte(it) }
                            search.to?.let { lte(it) }
                        }
                    }
            )
            .with(Sort.by("date"))

        val schedules = mongoTemplate.find<InterviewSchedule>(query)

        return schedules
    }

    override fun update(scheduleId: String, changes: InterviewUpdateSpec) {

        val query = Query().apply {
            addCriteria(Criteria.where("_id").`is`(scheduleId))
        }

        val update = Update().apply {
            changes.company?.let { set("company", it) }
            changes.date?.let { set("date", it) }
            changes.position?.let { set("position", it) }
            changes.category?.let { set("category", it) }
            changes.description?.let { set("description", it) }
            set("updatedAt", Clock.System.now())
        }

        val result = mongoTemplate.updateFirst<InterviewSchedule>(
            query,
            update
        )

        if (result.matchedCount == 0L) {
            throw CustomException(ErrorCode.DOCUMENT_NOT_FOUND)
        }
    }

    override fun save(schedule: InterviewSchedule): InterviewSchedule = mongoRepo.save(schedule)

    override fun deleteByScheduleId(scheduleId: String) = mongoRepo.deleteById(scheduleId)
}

interface SpringDataScheduleRepository : MongoRepository<InterviewSchedule, String>

fun Query.andIfNotNull(criteria: Criteria?) = apply {
    criteria?.let { addCriteria(it) }
}