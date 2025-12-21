package com.pickme.calendar.entity

import lombok.RequiredArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.lang.NonNull

@RequiredArgsConstructor
@Document(collection = "calendar")
data class Calendar(
    @Id
    val id: String = org.bson.types.ObjectId().toString(),

    @NonNull
    val clientId: String,

    @NonNull
    var interviewDetails: MutableList<InterviewDetail> = mutableListOf(),
)
