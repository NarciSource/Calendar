package com.pickme.calendar.domain.model

import com.pickme.calendar.domain.model.InterviewDetail.Company
import java.util.*

data class InterviewUpdateSpec(
    val company: Company?,
    val interviewTime: Date?,
    val position: String?,
    val category: String?,
    val description: String?,
)