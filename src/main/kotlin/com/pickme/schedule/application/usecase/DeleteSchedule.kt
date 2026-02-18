package com.pickme.schedule.application.usecase

import com.pickme.schedule.application.annotation.UseCase
import com.pickme.schedule.application.port.out.ScheduleRepository

@UseCase
class DeleteScheduleUseCase(
    private val repository: ScheduleRepository
) {
    fun execute(command: DeleteScheduleCommand) {

        repository.deleteByScheduleId(command.scheduleId, command.clientId)
    }
}

data class DeleteScheduleCommand(
    val scheduleId: String,
    val clientId: String
)