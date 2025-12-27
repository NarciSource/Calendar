package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.CalendarRepository
import com.pickme.calendar.domain.model.InterviewUpdateSpec
import java.util.function.Supplier

// 사용자의 면접 일정 수정
@UseCase
class UpdateScheduleUseCase(
    private val repository: CalendarRepository,
) {
    fun execute(command: UpdateScheduleCommand) {
        val calendar = repository
            .findByScheduleId(command.scheduleId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        val schedule = calendar.schedules.stream()
            .filter { it.id == command.scheduleId }
            .findFirst()
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        // 수정 업데이트
        schedule.update(command.changes)
        // 수정된 Calendar 객체를 데이터베이스에 저장
        repository.save(calendar)
    }
}

data class UpdateScheduleCommand(
    val scheduleId: String,
    val changes: InterviewUpdateSpec
)