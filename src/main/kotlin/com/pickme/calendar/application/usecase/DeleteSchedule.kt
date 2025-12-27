package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.CalendarRepository
import java.util.function.Supplier

// 사용자의 면접 일정 삭제
@UseCase
class DeleteScheduleUseCase(
    private val repository: CalendarRepository
) {
    fun execute(command: DeleteScheduleCommand) {
        val calendar = repository
            .findByScheduleId(command.scheduleId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        val schedule = calendar.schedules.stream()
            .filter { it.id == command.scheduleId }
            .findFirst()
            .orElseThrow<CustomException>(Supplier {
                CustomException(ErrorCode.DOCUMENT_NOT_FOUND)
            })

        calendar.schedules.remove(schedule) // 면접 일정 리스트에서 해당 항목 삭제

        repository.save(calendar)
    }
}

data class DeleteScheduleCommand(
    val scheduleId: String
)