package com.pickme.calendar.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class InterviewDetail {

	@Indexed
	private String interviewDetailId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Company company;

	private Date interviewTime;

	private String position;

	private String category;

	private String description;

	@Builder
	private InterviewDetail() {
		this.interviewDetailId = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = this.createdAt;
	}

	@Data
	@NoArgsConstructor
	public static class Company {

		private String name;

		private String location;
	}
}
