package com.pickme.calendar.domain.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.util.*

abstract class Schedule(
    @Id
    var id: String = ObjectId.get().toHexString(),

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Indexed
    val clientId: String,

    var date: Date
) {
    fun touch() {
        this.updatedAt = LocalDateTime.now()
    }

    fun isInYearMonth(yearMonth: YearMonth): Boolean {
        val time = date
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        return time.year == yearMonth.year &&
                time.monthValue == yearMonth.monthValue
    }
}
