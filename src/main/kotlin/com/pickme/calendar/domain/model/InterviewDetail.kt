package com.pickme.calendar.domain.model

import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime
import java.util.*

data class InterviewDetail(
    @Indexed
    val interviewDetailId: String = UUID.randomUUID().toString(),

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now(),

    var company: Company,

    var interviewTime: Date,

    var position: String,

    var category: String,

    var description: String,
) {
    fun touch() {
        this.updatedAt = LocalDateTime.now()
    }

    fun update(command: InterviewUpdateSpec) {
        command.company?.let { this.company = it }
        command.interviewTime?.let { this.interviewTime = it }
        command.position?.let { this.position = it }
        command.category?.let { this.category = it }
        command.description?.let { this.description = it }
        touch()
    }

    data class Company(
        val name: String,

        val location: String,
    )
}
