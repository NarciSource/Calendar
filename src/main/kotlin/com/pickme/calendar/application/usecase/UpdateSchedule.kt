package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewUpdateSpec
import java.util.function.Supplier

@UseCase
class UpdateScheduleUseCase(
    private val repository: ScheduleRepository,
) {
    fun execute(command: UpdateScheduleCommand) {

        val schedule = repository.findByScheduleId(command.scheduleId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        schedule.update(command.changes)

        repository.save(schedule)
    }
}

data class UpdateScheduleCommand(
    val scheduleId: String,
    val changes: InterviewUpdateSpec
)