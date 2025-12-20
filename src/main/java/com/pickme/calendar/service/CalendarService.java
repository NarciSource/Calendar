package com.pickme.calendar.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pickme.calendar.dto.request.GetInterviewDto;
import com.pickme.calendar.dto.request.PostInterviewDto;
import com.pickme.calendar.dto.request.PutInterviewDto;
import com.pickme.calendar.dto.response.CalendarDto;
import com.pickme.calendar.entity.Calendar;
import com.pickme.calendar.entity.InterviewDetail;
import com.pickme.calendar.exception.CustomException;
import com.pickme.calendar.exception.ErrorCode;
import com.pickme.calendar.repository.CalendarMongoQueryProcessor;
import com.pickme.calendar.repository.CalendarRepository;
import com.pickme.calendar.service.mapper.CalendarMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
	private final CalendarRepository calendarRepository;
	private final CalendarMapper calendarMapper;
	private final CalendarMongoQueryProcessor calendarMongoQueryProcessor;

	// 해당 사용자의 필터 조건에 맞는 면접 일정 조회
	public CalendarDto interviewsList(String clientId, String name, YearMonth yearMonth) {

		// 사용자의 면접 일정 전체를 Calendar 객체로 가져옴
		Calendar calendar = calendarRepository
			.findByClientId(clientId)
			.orElseThrow(() -> new CustomException(ErrorCode.DOCUMENT_NOT_FOUND));
		// 주어진 조건(현재는 name)으로 필터링된 interviewDetails 리스트를 가져옴
		List<InterviewDetail> interviewDetails = calendarMongoQueryProcessor.filterInterviewDetails(
			calendar, name, yearMonth);

		// Calendar 엔티티의 정보를 GetCalendarDTO로 매핑
		CalendarDto calendarDto = calendarMapper.toDto(calendar);

		// interviewDetails 리스트를 GetInterviewDetailDTO 객체로 변환
		List<GetInterviewDto> getInterviewListDtoList = calendarMapper.toDto(interviewDetails);
		// 변환된 interviewDetails 리스트를 GetCalendarDTO 객체에 설정
		calendarDto.setInterviewDetails(getInterviewListDtoList);

		return calendarDto;
	}

	// interviewDetailId에 해당하는 면접 일정 조회
	public GetInterviewDto getInterview(String interviewDetailId) {
		Calendar calendar = calendarRepository
			.findByInterviewDetails_interviewDetailId(interviewDetailId)
			.orElseThrow(() -> new CustomException((ErrorCode.DOCUMENT_NOT_FOUND)));

		// interviewDetailId에 해당하는 일정 가져옴
		InterviewDetail interviewDetail = calendarMongoQueryProcessor
			.findInterviewDetail(calendar, interviewDetailId)
			.orElseThrow(() -> new CustomException((ErrorCode.DOCUMENT_NOT_FOUND)));

		GetInterviewDto getInterviewDto = calendarMapper.toDto(interviewDetail);

		return getInterviewDto;
	}

	// 사용자의 면접 일정 추가
	public boolean registerInterviewSchedule(PostInterviewDto postInterviewDto, String clientId) {

		Calendar calendar = calendarRepository.findByClientId(clientId)
			.orElseGet(() -> new Calendar(clientId, new ArrayList<>()));

		// 전달받은 DTO(PostInterviewDetailDTO)를 InterviewDetail 객체로 변환
		InterviewDetail interviewDetail = calendarMapper.toEntity(postInterviewDto);
		// 변환된 interviewDetail을 Calendar의 interviewDetails 리스트에 추가
		calendar.getInterviewDetails().add(interviewDetail);
		// 업데이트된 Calendar 객체를 데이터베이스에 저장
		calendarRepository.save(calendar);

		return true;
	}

	// 사용자의 면접 일정 삭제
	public boolean deleteInterviewSchedule(String interviewDetailId) {

		Calendar calendar = calendarRepository
			.findByInterviewDetails_interviewDetailId(interviewDetailId)
			.orElseThrow(() -> new CustomException((ErrorCode.DOCUMENT_NOT_FOUND)));

		InterviewDetail interviewDetail =
			calendarMongoQueryProcessor.findInterviewDetail(calendar, interviewDetailId)
				.orElseThrow(() ->
					new CustomException(ErrorCode.DOCUMENT_NOT_FOUND)
				);

		calendarMongoQueryProcessor.deleteInterview(calendar, interviewDetail);

		return true;
	}

	// 사용자의 면접 일정 수정
	public boolean putInterviewSchedule(String interviewDetailId, PutInterviewDto putInterviewDto) {

		Calendar calendar = calendarRepository
			.findByInterviewDetails_interviewDetailId(interviewDetailId)
			.orElseThrow(() -> new CustomException((ErrorCode.DOCUMENT_NOT_FOUND)));

		InterviewDetail interviewDetail = calendarMongoQueryProcessor
			.findInterviewDetail(calendar, interviewDetailId)
			.orElseThrow(() -> new CustomException((ErrorCode.DOCUMENT_NOT_FOUND)));

		// 수정할 데이터를 받아온 DTO를 면접 일정 객체에 매핑하여 수정
		calendarMapper.toEntity(putInterviewDto, interviewDetail);
		// 수정 업데이트
		interviewDetail.touch();
		// 수정된 Calendar 객체를 데이터베이스에 저장
		calendarRepository.save(calendar);

		return true;
	}
}
