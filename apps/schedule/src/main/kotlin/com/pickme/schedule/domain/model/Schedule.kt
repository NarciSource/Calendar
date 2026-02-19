package com.pickme.schedule.domain.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.*

abstract class Schedule(
    @Id
    var id: String = ObjectId.get().toHexString(),

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now(),

    val clientId: String,

    var date: Date
)
