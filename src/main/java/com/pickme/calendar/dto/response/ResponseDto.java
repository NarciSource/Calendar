package com.pickme.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "응답")
@AllArgsConstructor
public class ResponseDto {
	@Schema(description = "성공 유무", example = "false")
	private Boolean success;

	@Schema(description = "응답 메시지", example = "interviewDetailId에 해당하는 면접 일정이 없습니다.")
	private String message;

	@Schema(description = "면접 일정 ID", example = "fd3c55f5-07fe-4d0c-94d3-69cbaeb2646c")
	private String interviewDetailId;
}
