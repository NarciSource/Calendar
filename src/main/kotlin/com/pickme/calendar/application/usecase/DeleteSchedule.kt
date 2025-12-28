package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.ScheduleRepository

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