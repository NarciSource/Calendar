package com.pickme.calendar.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Document(collection = "calendar")
@Getter
@RequiredArgsConstructor
public class Calendar {

	@Id
	private String id;

	@NonNull
	private String clientId;

	@NonNull
	private List<InterviewDetail> interviewDetails;
}
