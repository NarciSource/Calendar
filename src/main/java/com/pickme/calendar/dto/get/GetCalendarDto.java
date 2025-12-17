package com.pickme.calendar.dto.get;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "전체 조회 & 조건 조회 시 응답")
public class GetCalendarDto {
	@Schema(description = "clientId", example = "60011bt0ev46lpb51pbnpugpn7")
	private String clientId;
	private List<GetInterviewDetailDto> interviewDetails;
}
