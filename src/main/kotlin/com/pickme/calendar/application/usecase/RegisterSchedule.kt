package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.CalendarRepository
import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewSchedule
import java.util.function.Supplier

// 사용자의 면접 일정 추가
@UseCase
class RegisterScheduleUseCase(
    private val repository: CalendarRepository
) {
    fun execute(command: RegisterScheduleCommand): String {
        val calendar = repository.findByClientId(command.clientId)
            .orElseGet(Supplier {
                Calendar(clientId = command.clientId)
            })

        // 변환된 interviewDetail을 Calendar의 interviewDetails 리스트에 추가
        calendar.schedules.add(command.schedule)
        // 업데이트된 Calendar 객체를 데이터베이스에 저장
        repository.save(calendar)

        return command.schedule.id
    }
}

data class RegisterScheduleCommand(
    val clientId: String,
    val schedule: InterviewSchedule
)