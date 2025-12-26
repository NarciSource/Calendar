package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.port.out.CalendarRepository
import com.pickme.calendar.domain.model.InterviewDetail
import java.util.function.Supplier

// interviewDetailId에 해당하는 면접 일정 조회
@UseCase
class GetInterviewUseCase(
    private val repository: CalendarRepository
) {
    fun execute(query: GetInterviewQuery): InterviewDetail {
        val calendar = repository
            .findByInterviewId(query.interviewId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        // interviewDetailId에 해당하는 일정 가져옴
        val interviewDetail = calendar.interviewDetails.stream()
            .filter { interviewDetails -> interviewDetails.interviewDetailId == query.interviewId }
            .findFirst()
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        return interviewDetail
    }
}

data class GetInterviewQuery(
    val interviewId: String
)
