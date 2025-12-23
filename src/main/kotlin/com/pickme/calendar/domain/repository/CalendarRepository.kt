package com.pickme.calendar.domain.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CalendarRepository : MongoRepository<Calendar, String> {
    // 해당 사용자 면접 일정 정보 추출
    fun findByClientId(clientId: String): Optional<Calendar>

    fun findByInterviewDetails_interviewDetailId(interviewDetailId: String): Optional<Calendar>
}
