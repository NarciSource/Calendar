package com.pickme.calendar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories // mongodb 사용하기 위함
@SpringBootApplication
class CalendarApplication

fun main(args: Array<String>) {
    runApplication<CalendarApplication>(*args)
}