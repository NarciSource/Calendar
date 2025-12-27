package com.pickme.calendar.domain.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "calendar")
data class Calendar(
    @Id
    val id: String = ObjectId.get().toHexString(),

    val clientId: String,

    var schedules: MutableList<InterviewSchedule> = mutableListOf(),
)
