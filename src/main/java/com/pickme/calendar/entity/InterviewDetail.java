package com.pickme.calendar.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterviewDetail {

	@Indexed
	@Builder.Default
	private final String interviewDetailId = UUID.randomUUID().toString();

	@Builder.Default
	private final LocalDateTime createdAt = LocalDateTime.now();

	@Builder.Default
	private LocalDateTime updatedAt = LocalDateTime.now();

	private Company company;

	private Date interviewTime;

	private String position;

	private String category;

	private String description;

	public void touch() {
		this.updatedAt = LocalDateTime.now();
	}

	@Getter
	@Builder
	public static class Company {

		private String name;

		private String location;
	}
}
