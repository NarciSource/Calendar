package com.pickme.calendar.entity

import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime
import java.util.*

data class InterviewDetail(
    @Indexed
    val interviewDetailId: String = UUID.randomUUID().toString(),

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now(),

    val company: Company,

    val interviewTime: Date,

    val position: String,

    val category: String,

    val description: String,
) {
    fun touch() {
        this.updatedAt = LocalDateTime.now()
    }

    data class Company(
        val name: String,

        val location: String,
    )
}
