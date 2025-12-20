package com.pickme.calendar.dto.request;

import java.time.LocalDateTime;
import java.util.Date;

import com.pickme.calendar.dto.request.payload.CompanyDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "면접 일정 조회")
public class GetInterviewDto {
	@Schema(description = "면접 일정 ID", example = "fd3c55f5-07fe-4d0c-94d3-69cbaeb2646c")
	private String interviewDetailId;

	@Schema(description = "생성 시간", example = "2025-03-05T13:43:35.901")
	private LocalDateTime createdAt;

	@Schema(description = "수정 시간", example = "2025-03-05T13:43:35.901")
	private LocalDateTime updatedAt;

	private CompanyDto company;

	@Schema(description = "면접 일정 시간", example = "2024-12-01T02:49:03.465+00:00")
	private Date interviewTime;

	@Schema(description = "직무", example = "바리스타")
	private String position;

	@Schema(description = "면접 유형", example = "1차 기술면접")
	private String category;

	@Schema(description = "추가 사항", example = "면접 전 15분 전까지 도착하기")
	private String description;
}
