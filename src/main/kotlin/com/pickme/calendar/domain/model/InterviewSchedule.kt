package com.pickme.calendar.domain.model

import com.pickme.calendar.domain.model.InterviewSchedule.Company
import java.util.*

class InterviewSchedule(
    date: Date,

    var company: Company,

    var position: String,

    var category: String,

    var description: String,
) : Schedule(date = date) {

    class Company(
        val name: String,

        val location: String,
    )

    fun update(command: InterviewUpdateSpec) {
        command.company?.let { this.company = it }
        command.date?.let { this.date = it }
        command.position?.let { this.position = it }
        command.category?.let { this.category = it }
        command.description?.let { this.description = it }
        touch()
    }

    fun isFromCompany(companyName: String): Boolean {
        return this.company.name.equals(companyName, ignoreCase = true)
    }
}

data class InterviewUpdateSpec(
    val company: Company?,
    val date: Date?,
    val position: String?,
    val category: String?,
    val description: String?,
)