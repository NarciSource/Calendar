package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.CalendarRepository
import com.pickme.calendar.domain.model.InterviewSchedule
import java.util.function.Supplier

// interviewDetailId에 해당하는 면접 일정 조회
@UseCase
class GetScheduleUseCase(
    private val repository: CalendarRepository
) {
    fun execute(query: GetScheduleQuery): InterviewSchedule {
        val calendar = repository
            .findByScheduleId(query.scheduleId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        // interviewDetailId에 해당하는 일정 가져옴
        val schedule = calendar.schedules.stream()
            .filter { it.id == query.scheduleId }
            .findFirst()
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        return schedule
    }
}

data class GetScheduleQuery(
    val scheduleId: String
)
