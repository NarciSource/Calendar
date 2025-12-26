package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.port.out.CalendarRepository
import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewDetail
import java.util.function.Supplier

// 사용자의 면접 일정 추가
@UseCase
class RegisterInterviewUseCase(
    private val repository: CalendarRepository
) {
    fun execute(command: RegisterInterviewCommand): String {
        val calendar = repository.findByClientId(command.clientId)
            .orElseGet(Supplier {
                Calendar(clientId = command.clientId)
            })

        // 변환된 interviewDetail을 Calendar의 interviewDetails 리스트에 추가
        calendar.interviewDetails.add(command.interview)
        // 업데이트된 Calendar 객체를 데이터베이스에 저장
        repository.save(calendar)

        return command.interview.interviewDetailId
    }
}

data class RegisterInterviewCommand(
    val clientId: String,
    val interview: InterviewDetail
)