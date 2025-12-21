package com.pickme.calendar.entity

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime
import java.util.*

data class InterviewDetail(
    @Indexed
    @Builder.Default
    val interviewDetailId: String = UUID.randomUUID().toString(),

    @Builder.Default
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Builder.Default
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
