package com.pickme.calendar.application.usecase

import com.pickme.calendar.application.annotation.UseCase
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewDetail
import com.pickme.calendar.domain.repository.CalendarRepository
import java.time.YearMonth
import java.time.ZoneId
import java.util.function.Supplier

// 해당 사용자의 필터 조건에 맞는 면접 일정 조회
@UseCase
class FindInterviewsUseCase(
    private val repository: CalendarRepository
) {
    fun execute(query: FindInterviewsQuery): FindInterviewsResult {
        // 사용자의 면접 일정 전체를 Calendar 객체로 가져옴

        val calendar = repository
            .findByClientId(query.clientId)
            .orElseThrow<CustomException>(Supplier { CustomException(ErrorCode.DOCUMENT_NOT_FOUND) })
        // 주어진 조건(현재는 name)으로 필터링된 interviewDetails 리스트를 가져옴

        val interviewDetails = calendar.interviewDetails.stream() // 면접 일정 리스트를 스트림으로 변환
            .filter { interviewDetail ->
                query.name == null || interviewDetail.company.name == query.name
            } // 회사 이름 필터링
            .filter { interviewDetail ->
                if (query.yearMonth != null) {
                    // yearMonth로부터 연도와 월 추출
                    val year = query.yearMonth.year // 연도 추출
                    val month = query.yearMonth.monthValue // 월 추출

                    // interviewTime의 연도와 월 비교
                    val interviewTime = interviewDetail.interviewTime
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()

                    return@filter interviewTime.year == year && interviewTime.monthValue == month
                }
                true // yearMonth가 없으면 필터링하지 않음
            }
            .toList() // 결과를 리스트로 반환

        return FindInterviewsResult(
            calendar = calendar,
            interviewDetails = interviewDetails
        )
    }
}

data class FindInterviewsQuery(
    val clientId: String,
    val name: String?,
    val yearMonth: YearMonth?
)

data class FindInterviewsResult(
    val calendar: Calendar,
    val interviewDetails: List<InterviewDetail>
)