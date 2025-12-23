package com.pickme.calendar.application.usecase

import com.pickme.calendar.adapter.inbound.web.dto.request.PutInterviewDto
import com.pickme.calendar.adapter.inbound.web.mapper.CalendarMapper
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import com.pickme.calendar.application.result.InterviewListResult
import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewDetail
import com.pickme.calendar.domain.repository.CalendarRepository
import org.springframework.stereotype.Service
import java.time.YearMonth
import java.util.function.Supplier

@Service
class InterviewService(
    val calendarRepository: CalendarRepository,
    val calendarMapper: CalendarMapper,
    val interviewQueryProcessor: InterviewQueryProcessor,
) {

    // 해당 사용자의 필터 조건에 맞는 면접 일정 조회
    fun interviewsList(clientId: String, name: String?, yearMonth: YearMonth?): InterviewListResult {
        // 사용자의 면접 일정 전체를 Calendar 객체로 가져옴

        val calendar = calendarRepository
            .findByClientId(clientId)
            .orElseThrow<CustomException>(Supplier { CustomException(ErrorCode.DOCUMENT_NOT_FOUND) })
        // 주어진 조건(현재는 name)으로 필터링된 interviewDetails 리스트를 가져옴
        val interviewDetails = interviewQueryProcessor
            .filterInterviewDetails(
                calendar, name, yearMonth
            )

        return InterviewListResult(
            calendar = calendar,
            interviewDetails = interviewDetails
        )
    }

    // interviewDetailId에 해당하는 면접 일정 조회
    fun getInterview(interviewDetailId: String): InterviewDetail {
        val calendar = calendarRepository
            .findByInterviewDetails_interviewDetailId(interviewDetailId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        // interviewDetailId에 해당하는 일정 가져옴
        val interviewDetail = interviewQueryProcessor
            .findInterviewDetail(calendar, interviewDetailId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        return interviewDetail
    }

    // 사용자의 면접 일정 추가
    fun registerInterviewSchedule(interviewDetail: InterviewDetail, clientId: String): String {
        val calendar = calendarRepository.findByClientId(clientId)
            .orElseGet(Supplier {
                Calendar(clientId = clientId)
            })

        // 변환된 interviewDetail을 Calendar의 interviewDetails 리스트에 추가
        calendar.interviewDetails.add(interviewDetail)
        // 업데이트된 Calendar 객체를 데이터베이스에 저장
        val result = calendarRepository.save<Calendar>(calendar)

        return result.id
    }

    // 사용자의 면접 일정 수정
    fun putInterviewSchedule(interviewDetailId: String, putInterviewDto: PutInterviewDto): String {
        val calendar = calendarRepository
            .findByInterviewDetails_interviewDetailId(interviewDetailId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        val interviewDetail = interviewQueryProcessor
            .findInterviewDetail(calendar, interviewDetailId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        // 수정할 데이터를 받아온 DTO를 면접 일정 객체에 매핑하여 수정
        calendarMapper.toEntity(putInterviewDto, interviewDetail)
        // 수정 업데이트
        interviewDetail.touch()
        // 수정된 Calendar 객체를 데이터베이스에 저장
        val saved = calendarRepository.save<Calendar>(calendar)

        return saved.id
    }

    // 사용자의 면접 일정 삭제
    fun deleteInterviewSchedule(interviewDetailId: String): Boolean {
        val calendar = calendarRepository
            .findByInterviewDetails_interviewDetailId(interviewDetailId)
            .orElseThrow<CustomException>(Supplier {
                CustomException((ErrorCode.DOCUMENT_NOT_FOUND))
            })

        val interviewDetail = interviewQueryProcessor
            .findInterviewDetail(calendar, interviewDetailId)
            .orElseThrow<CustomException>(Supplier {
                CustomException(ErrorCode.DOCUMENT_NOT_FOUND)
            })

        interviewQueryProcessor.deleteInterview(calendar, interviewDetail)

        return true
    }
}