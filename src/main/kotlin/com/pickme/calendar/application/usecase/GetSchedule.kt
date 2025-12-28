package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import java.util.function.Supplier

@UseCase
class GetScheduleUseCase(
    private val repository: ScheduleRepository
) {
    fun execute(query: GetScheduleQuery): InterviewSchedule {

        val schedule = repository.findByScheduleId(query.scheduleId, query.clientId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        return schedule
    }
}

data class GetScheduleQuery(
    val scheduleId: String,
    val clientId: String
)
