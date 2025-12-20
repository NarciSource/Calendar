package com.pickme.calendar.dto.request.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "회사 정보")
public class CompanyDto {
	@Schema(description = "회사명", example = "앙떼띠")
	private String name;

	@Schema(description = "회사 위치", example = "잠실")
	private String location;
}
