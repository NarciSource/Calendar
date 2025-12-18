package com.pickme.calendar.dto.request.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CompanyDto {
	@Schema(description = "회사명", example = "앙떼띠")
	private String name;

	@Schema(description = "회사 위치", example = "잠실")
	private String location;
}
