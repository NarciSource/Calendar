package com.pickme.calendar.infrastructure.schema

import com.pickme.calendar.domain.model.InterviewSchedule
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.data.mongodb.core.indexOps
import org.springframework.stereotype.Component

@Component
@Profile("!openapi")
class MongoIndexInitializer(
    private val mongoTemplate: MongoTemplate
) {

    @PostConstruct
    fun initIndexes() {
        val indexOps = mongoTemplate.indexOps<InterviewSchedule>()

        indexOps.ensureIndex(
            Index()
                .on("clientId", Sort.Direction.ASC)
                .on("date", Sort.Direction.ASC)
                .named("idx_client_date")
        )

        indexOps.ensureIndex(
            Index()
                .on("clientId", Sort.Direction.ASC)
                .on("category", Sort.Direction.ASC)
                .on("date", Sort.Direction.ASC)
                .named("idx_client_category_date")
        )
    }
}