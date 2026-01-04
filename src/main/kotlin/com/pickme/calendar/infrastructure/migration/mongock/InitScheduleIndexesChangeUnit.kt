package com.pickme.calendar.infrastructure.migration.mongock

import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index

@ChangeUnit(
    id = "init-schedule-indexes",
    order = "001",
    author = "calendar-backend"
)
class InitScheduleIndexesChangeUnit {

    @Execution
    fun execution(mongoTemplate: MongoTemplate) {
        val indexOps = mongoTemplate.indexOps("schedule")

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

    @RollbackExecution
    fun rollback(mongoTemplate: MongoTemplate) {
        val indexOps = mongoTemplate.indexOps("schedule")

        indexOps.dropIndex("idx_client_date")
        indexOps.dropIndex("idx_client_category_date")
    }
}