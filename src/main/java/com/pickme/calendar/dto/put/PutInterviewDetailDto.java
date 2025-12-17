package com.pickme.calendar.dto.put;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PutInterviewDetailDto {

	private PutCompanyDto company;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date interviewTime;

	@Schema(example = "수정된 직무")
	private String position;

	@Schema(example = "수정된 면접 유형")
	private String category;

	@Schema(example = "수정된 사항")
	private String description;
}
