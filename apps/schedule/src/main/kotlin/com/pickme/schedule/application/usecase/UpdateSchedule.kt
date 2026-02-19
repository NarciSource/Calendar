package com.pickme.schedule.application.usecase

import com.pickme.schedule.application.annotation.UseCase
import com.pickme.schedule.application.port.out.ScheduleRepository
import com.pickme.schedule.domain.model.InterviewUpdateSpec

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