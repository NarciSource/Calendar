package com.pickme.schedule.application.usecase

import com.pickme.schedule.application.annotation.UseCase
import com.pickme.schedule.application.exception.CustomException
import com.pickme.schedule.application.exception.ErrorCode
import com.pickme.schedule.application.port.out.ScheduleRepository
import com.pickme.schedule.domain.model.InterviewSchedule

@UseCase
class GetScheduleUseCase(
    private val repository: ScheduleRepository
) {
    fun execute(query: GetScheduleQuery): InterviewSchedule {

        val schedule = repository.findByScheduleId(query.scheduleId, query.clientId)
            ?: throw CustomException(ErrorCode.DOCUMENT_NOT_FOUND)
        return schedule
    }
}

data class GetScheduleQuery(
    val scheduleId: String,
    val clientId: String
)
