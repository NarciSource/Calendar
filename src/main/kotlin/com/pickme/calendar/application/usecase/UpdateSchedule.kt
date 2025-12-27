package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewUpdateSpec

@UseCase
class UpdateScheduleUseCase(
    private val repository: ScheduleRepository,
) {
    fun execute(command: UpdateScheduleCommand) {

        repository.update(command.scheduleId, command.changes)
    }
}

data class UpdateScheduleCommand(
    val scheduleId: String,
    val changes: InterviewUpdateSpec
)