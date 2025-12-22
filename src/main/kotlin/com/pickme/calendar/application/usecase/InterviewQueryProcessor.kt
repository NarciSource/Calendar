package com.pickme.calendar.application.usecase

import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewDetail
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import java.time.YearMonth
import java.time.ZoneId
import java.util.*

@Component
class InterviewQueryProcessor(
    val mongoTemplate: MongoTemplate
) {
    // 주어진 Calendar 객체에서 면접 일정 목록을 가져오는 메서드
    // 주어진 조건(interviewDetailId, name, yearMonth)이 null이 아니면 해당 조건과 일치하는 면접 일정만 필터링하여 반환
    // 조건(interviewDetailId, name, yearMonth)이 null인 경우 모든 면접 일정을 반환
    fun filterInterviewDetails(
        calendar: Calendar,
        name: String?,
        yearMonth: YearMonth?
    ): List<InterviewDetail> {
        return calendar.interviewDetails.stream() // 면접 일정 리스트를 스트림으로 변환
            .filter { interviewDetail ->
                name == null || interviewDetail.company.name == name
            } // 회사 이름 필터링
            .filter { interviewDetail ->
                if (yearMonth != null) {
                    // yearMonth로부터 연도와 월 추출
                    val year = yearMonth.year // 연도 추출
                    val month = yearMonth.monthValue // 월 추출

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
    }

    // ClientId 사용자의 interviewDetailId에 해당하는 일정 가져오는 메서드.
    fun findInterviewDetail(calendar: Calendar, interviewDetailId: String): Optional<InterviewDetail> {
        // 스트림을 사용하여 주어진 interviewDetailId에 해당하는 면접 일정을 찾음
        return calendar.interviewDetails.stream()
            .filter { interviewDetails ->
                interviewDetails.interviewDetailId == interviewDetailId
            } // 조건에 맞는 일정 필터링
            .findFirst() // 첫 번째 요소를 Optional로 반환
    }

    // 주어진 Calendar 객체에서 특정 면접 일정(interviewDetailId)을 삭제하는 메서드.
    fun deleteInterview(calendar: Calendar, details: InterviewDetail) {
        calendar.interviewDetails.remove(details) // 면접 일정 리스트에서 해당 항목 삭제

        mongoTemplate.save<Calendar>(calendar) // 변경된 Calendar 객체를 MongoDB에 저장
    }
}