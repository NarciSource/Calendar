package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewUpdateSpec
import com.pickme.calendar.domain.repository.CalendarRepository
import java.util.function.Supplier

// 사용자의 면접 일정 수정
@UseCase
class UpdateInterviewUseCase(
    private val repository: CalendarRepository,
) {
    fun execute(command: UpdateInterviewCommand) {
        val calendar = repository
            .findByInterviewDetails_interviewDetailId(command.interviewId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        val interviewDetail = calendar.interviewDetails.stream()
            .filter { interviewDetails -> interviewDetails.interviewDetailId == command.interviewId }
            .findFirst()
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        // 수정 업데이트
        interviewDetail.update(command.interviewChanges)
        // 수정된 Calendar 객체를 데이터베이스에 저장
        repository.save<Calendar>(calendar)
    }
}

data class UpdateInterviewCommand(
    val interviewId: String,
    val interviewChanges: InterviewUpdateSpec
)