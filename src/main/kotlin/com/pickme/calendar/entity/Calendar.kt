package com.pickme.calendar.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "calendar")
data class Calendar(
    @Id
    val id: String = org.bson.types.ObjectId().toString(),

    val clientId: String,

    var interviewDetails: MutableList<InterviewDetail> = mutableListOf(),
)
