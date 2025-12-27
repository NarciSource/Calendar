package com.pickme.calendar.adapter.inbound.web.mapper

import com.pickme.calendar.adapter.inbound.web.dto.request.GetScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PostScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PutScheduleDto
import com.pickme.calendar.adapter.inbound.web.dto.request.SearchQueryDto
import com.pickme.calendar.adapter.inbound.web.dto.request.payload.CompanyDto
import com.pickme.calendar.domain.model.InterviewSchedule
import com.pickme.calendar.domain.model.InterviewSearchSpec
import com.pickme.calendar.domain.model.InterviewUpdateSpec
import org.mapstruct.Mapper
import org.mapstruct.ObjectFactory

@Mapper(componentModel = "spring") // Spring Bean으로 등록
interface InterviewScheduleMapper {

    fun toDto(interviewSchedules: List<InterviewSchedule>): List<GetScheduleDto>

    fun toDto(interviewSchedule: InterviewSchedule): GetScheduleDto

    fun toEntity(dto: CompanyDto): InterviewSchedule.Company

    fun toEntity(dto: PutScheduleDto): InterviewUpdateSpec

    @ObjectFactory
    fun toEntity(dto: PostScheduleDto, clientId: String): InterviewSchedule {
        return InterviewSchedule(
            clientId = clientId,
            company = toEntity(dto.company),
            date = dto.date,
            position = dto.position,
            category = dto.category,
            description = dto.description
        )
    }

    fun toEntity(dto: SearchQueryDto): InterviewSearchSpec
}
