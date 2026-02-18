package com.pickme.schedule.application.usecase

import com.pickme.schedule.application.annotation.UseCase
import com.pickme.schedule.application.port.out.ScheduleRepository
import com.pickme.schedule.domain.model.InterviewSchedule

@UseCase
class RegisterScheduleUseCase(
    private val repository: ScheduleRepository
) {
    fun execute(command: RegisterScheduleCommand): String {

        repository.save(command.schedule)

        return command.schedule.id
    }
}

data class RegisterScheduleCommand(
    val clientId: String,
    val schedule: InterviewSchedule
)