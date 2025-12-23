package com.pickme.calendar.application.result

import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewDetail

data class InterviewListResult(
    val calendar: Calendar,
    val interviewDetails: List<InterviewDetail>
)