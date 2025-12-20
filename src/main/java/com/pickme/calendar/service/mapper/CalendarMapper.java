package com.pickme.calendar.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pickme.calendar.dto.request.GetInterviewDto;
import com.pickme.calendar.dto.request.PostInterviewDto;
import com.pickme.calendar.dto.request.PutInterviewDto;
import com.pickme.calendar.dto.response.CalendarDto;
import com.pickme.calendar.entity.Calendar;
import com.pickme.calendar.entity.InterviewDetail;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface CalendarMapper {

	@Mapping(target = "interviewDetails", ignore = true)
	CalendarDto toDto(Calendar calendar);

	List<GetInterviewDto> toDto(List<InterviewDetail> interviewDetails);

	GetInterviewDto toDto(InterviewDetail interviewDetail);

	@Mapping(target = "interviewDetailId", ignore = true)
	void toEntity(PostInterviewDto postInterviewDto, @MappingTarget InterviewDetail interviewDetail);

	@Mapping(target = "interviewDetailId", ignore = true)
	void toEntity(PutInterviewDto putInterviewDto, @MappingTarget InterviewDetail interviewDetail);
}
