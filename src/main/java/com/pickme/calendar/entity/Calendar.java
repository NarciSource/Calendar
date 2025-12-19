package com.pickme.calendar.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Document(collection = "calendar")
@Data
@RequiredArgsConstructor
public class Calendar {

	@Id
	private String id;

	@NonNull
	private String clientId;

	@NonNull
	private List<InterviewDetails> interviewDetails;

	@Data
	@NoArgsConstructor
	public static class InterviewDetails {
		@Indexed
		private String interviewDetailId;

		private LocalDateTime createdAt;

		private LocalDateTime updatedAt;

		private Company company;

		private Date interviewTime;

		private String position;

		private String category;

		private String description;
	}

	@Data
	@NoArgsConstructor
	public static class Company {

		private String name;

		private String location;

	}
}
