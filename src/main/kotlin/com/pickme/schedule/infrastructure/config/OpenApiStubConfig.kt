package com.pickme.schedule.infrastructure.config

import com.pickme.schedule.application.port.out.ScheduleRepository
import com.pickme.schedule.domain.model.InterviewSchedule
import com.pickme.schedule.domain.model.InterviewSearchSpec
import com.pickme.schedule.domain.model.InterviewUpdateSpec
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("openapi")
class OpenApiStubConfig {

    @Bean
    fun scheduleRepository(): ScheduleRepository =
        object : ScheduleRepository {
            override fun findByScheduleId(
                scheduleId: String,
                clientId: String
            ): InterviewSchedule? {
                TODO("Not yet implemented")
            }

            override fun findByScheduleIdIn(
                scheduleIds: List<String>
            ): List<InterviewSchedule> {
                TODO("Not yet implemented")
            }

            override fun find(
                search: InterviewSearchSpec,
                clientId: String
            ): List<InterviewSchedule> {
                TODO("Not yet implemented")
            }

            override fun save(schedule: InterviewSchedule): InterviewSchedule {
                TODO("Not yet implemented")
            }

            override fun update(
                changes: InterviewUpdateSpec,
                scheduleId: String,
                clientId: String
            ) {
                TODO("Not yet implemented")
            }

            override fun deleteByScheduleId(scheduleId: String, clientId: String) {
                TODO("Not yet implemented")
            }
        }
}