package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.ScheduleRepository
import com.pickme.calendar.domain.model.InterviewSchedule

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