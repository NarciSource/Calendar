package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.CalendarRepository
import java.util.function.Supplier

// 사용자의 면접 일정 삭제
@UseCase
class DeleteInterviewUseCase(
    private val repository: CalendarRepository
) {
    fun execute(command: DeleteInterviewCommand) {
        val calendar = repository
            .findByInterviewId(command.interviewId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        val interviewDetail = calendar.interviewDetails.stream()
            .filter { interviewDetails -> interviewDetails.interviewDetailId == command.interviewId }
            .findFirst()
            .orElseThrow<CustomException>(Supplier {
                CustomException(ErrorCode.DOCUMENT_NOT_FOUND)
            })

        calendar.interviewDetails.remove(interviewDetail) // 면접 일정 리스트에서 해당 항목 삭제

        repository.save(calendar)
    }
}

data class DeleteInterviewCommand(
    val interviewId: String
)