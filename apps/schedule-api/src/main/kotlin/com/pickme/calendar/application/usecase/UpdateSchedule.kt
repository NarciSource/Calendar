package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewUpdateSpec

@UseCase
class UpdateScheduleUseCase(
    private val repository: ScheduleRepository,
) {
    fun execute(command: UpdateScheduleCommand) {

        repository.update(command.changes, command.scheduleId, command.clientId)
    }
}

data class UpdateScheduleCommand(
    val changes: InterviewUpdateSpec,
    val scheduleId: String,
    val clientId: String
)