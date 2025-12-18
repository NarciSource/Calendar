package com.pickme.calendar.dto.response;

import java.util.List;

import com.pickme.calendar.dto.request.GetInterviewDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "전체 조회 & 조건 조회 시 응답")
public class CalendarDto {
	@Schema(description = "사용자 ID", example = "60011bt0ev46lpb51pbnpugpn7")
	private String clientId;

	@Schema(description = "면접 일정 목록")
	private List<GetInterviewDto> interviewDetails;
}
