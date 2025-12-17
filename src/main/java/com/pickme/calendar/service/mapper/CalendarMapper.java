package com.pickme.calendar.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pickme.calendar.dto.get.GetCalendarDto;
import com.pickme.calendar.dto.get.GetInterviewDetailDto;
import com.pickme.calendar.dto.get.GetInterviewDto;
import com.pickme.calendar.dto.post.PostInterviewDetailDto;
import com.pickme.calendar.dto.put.PutInterviewDetailDto;
import com.pickme.calendar.entity.Calendar;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface CalendarMapper {

	// 전달받은 DTO(PostInterviewDetailDTO)를 InterviewDetails 객체로 변환
	@Mapping(target = "interviewDetailId", ignore = true)
	void postInterviewDetailDtoToInterviewDetails(PostInterviewDetailDto postInterviewDetailDto,
		@MappingTarget Calendar.InterviewDetails interviewDetails);

	// Calendar 엔티티의 정보를 GetCalendarDTO로 매핑 (id, clientId)
	@Mapping(target = "interviewDetails", ignore = true)
	void calendarToGetCalendarDto(Calendar calendar, @MappingTarget GetCalendarDto getCalendarDto);

	// interviewDetails 리스트를 GetInterviewDetailDTO 객체로 변환
	List<GetInterviewDetailDto> interviewDetailsToGetInterviewDetailsDto(
		List<Calendar.InterviewDetails> interviewDetails);

	// interviewDetail을 GetInterviewDTO 객체로 변환
	GetInterviewDto interviewDetailToGetInterviewDto(Calendar.InterviewDetails interviewDetail);

	// 전달받은 DTO(PutInterviewDetailDTO)를 InterviewDetails 객체로 변환
	@Mapping(target = "interviewDetailId", ignore = true)
	void putInterviewDetailDtoToInterviewDetail(PutInterviewDetailDto putInterviewDetailDto,
		@MappingTarget Calendar.InterviewDetails interviewDetails);
}
