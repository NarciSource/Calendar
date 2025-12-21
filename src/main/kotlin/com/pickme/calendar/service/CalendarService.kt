package com.pickme.calendar.service

import com.pickme.calendar.dto.request.GetInterviewDto
import com.pickme.calendar.dto.request.PostInterviewDto
import com.pickme.calendar.dto.request.PutInterviewDto
import com.pickme.calendar.dto.response.CalendarDto
import com.pickme.calendar.entity.Calendar
import com.pickme.calendar.exception.CustomException
import com.pickme.calendar.exception.ErrorCode
import com.pickme.calendar.repository.CalendarMongoQueryProcessor
import com.pickme.calendar.repository.CalendarRepository
import com.pickme.calendar.service.mapper.CalendarMapper
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import java.time.YearMonth
import java.util.function.Supplier

@Service
@RequiredArgsConstructor
@Slf4j
class CalendarService(
    val calendarRepository: CalendarRepository,
    val calendarMapper: CalendarMapper,
    val calendarMongoQueryProcessor: CalendarMongoQueryProcessor,
) {

    // 해당 사용자의 필터 조건에 맞는 면접 일정 조회
    fun interviewsList(clientId: String, name: String?, yearMonth: YearMonth?): CalendarDto {
        // 사용자의 면접 일정 전체를 Calendar 객체로 가져옴

        val calendar = calendarRepository
            .findByClientId(clientId)
            .orElseThrow<CustomException>(Supplier { CustomException(ErrorCode.DOCUMENT_NOT_FOUND) })
        // 주어진 조건(현재는 name)으로 필터링된 interviewDetails 리스트를 가져옴
        val interviewDetails = calendarMongoQueryProcessor.filterInterviewDetails(
            calendar, name, yearMonth
        )

        // Calendar 엔티티의 정보를 GetCalendarDTO로 매핑
        val calendarDto = calendarMapper.toDto(calendar)

        // interviewDetails 리스트를 GetInterviewDetailDTO 객체로 변환
        val getInterviewListDtoList = calendarMapper.toDto(interviewDetails)
        // 변환된 interviewDetails 리스트를 GetCalendarDTO 객체에 설정
        calendarDto.interviewDetails = getInterviewListDtoList

        return calendarDto
    }

    // interviewDetailId에 해당하는 면접 일정 조회
    fun getInterview(interviewDetailId: String): GetInterviewDto {
        val calendar = calendarRepository
            .findByInterviewDetails_interviewDetailId(interviewDetailId)
            .orElseThrow<CustomException>(Supplier { CustomException((ErrorCode.DOCUMENT_NOT_FOUND)) })

        // interviewDetailId에 해당하는 일정 가져옴
        val interviewDetail = calendarMongoQueryProcessor
            .findInterviewDetail(calendar, interviewDetailId)
            .orElseThrow<CustomException>(Supplier { CustomException((ErrorCode.DOCUMENT_NOT_FOUND)) })

        val getInterviewDto = calendarMapper.toDto(interviewDetail)

        return getInterviewDto
    }

    // 사용자의 면접 일정 추가
    fun registerInterviewSchedule(postInterviewDto: PostInterviewDto, clientId: String): Boolean {
        val calendar: Calendar = calendarRepository.findByClientId(clientId)
            .orElseGet(Supplier {
                Calendar(clientId = clientId)
            })

        // 전달받은 DTO(PostInterviewDetailDTO)를 InterviewDetail 객체로 변환
        val interviewDetail = calendarMapper.toEntity(postInterviewDto)
        // 변환된 interviewDetail을 Calendar의 interviewDetails 리스트에 추가
        calendar.interviewDetails.add(interviewDetail)
        // 업데이트된 Calendar 객체를 데이터베이스에 저장
        calendarRepository.save<Calendar>(calendar)

        return true
    }

    // 사용자의 면접 일정 삭제
    fun deleteInterviewSchedule(interviewDetailId: String): Boolean {
        val calendar = calendarRepository
            .findByInterviewDetails_interviewDetailId(interviewDetailId)
            .orElseThrow<CustomException>(Supplier { CustomException((ErrorCode.DOCUMENT_NOT_FOUND)) })

        val interviewDetail =
            calendarMongoQueryProcessor.findInterviewDetail(calendar, interviewDetailId)
                .orElseThrow<CustomException>(Supplier { CustomException(ErrorCode.DOCUMENT_NOT_FOUND) }
                )

        calendarMongoQueryProcessor.deleteInterview(calendar, interviewDetail)

        return true
    }

    // 사용자의 면접 일정 수정
    fun putInterviewSchedule(interviewDetailId: String, putInterviewDto: PutInterviewDto): Boolean {
        val calendar = calendarRepository
            .findByInterviewDetails_interviewDetailId(interviewDetailId)
            .orElseThrow<CustomException>(Supplier { CustomException((ErrorCode.DOCUMENT_NOT_FOUND)) })

        val interviewDetail = calendarMongoQueryProcessor
            .findInterviewDetail(calendar, interviewDetailId)
            .orElseThrow<CustomException>(Supplier { CustomException((ErrorCode.DOCUMENT_NOT_FOUND)) })

        // 수정할 데이터를 받아온 DTO를 면접 일정 객체에 매핑하여 수정
        calendarMapper.toEntity(putInterviewDto, interviewDetail)
        // 수정 업데이트
        interviewDetail.touch()
        // 수정된 Calendar 객체를 데이터베이스에 저장
        calendarRepository.save<Calendar>(calendar)

        return true
    }
}
