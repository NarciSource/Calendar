package com.pickme.schedule.adapter.inbound.web.mapper

import com.pickme.schedule.adapter.inbound.web.dto.request.PostScheduleDto
import com.pickme.schedule.adapter.inbound.web.dto.request.PutScheduleDto
import com.pickme.schedule.adapter.inbound.web.dto.request.SearchQueryDto
import com.pickme.schedule.adapter.inbound.web.dto.request.payload.CompanyDto
import com.pickme.schedule.adapter.inbound.web.dto.response.ScheduleDto
import com.pickme.schedule.domain.model.InterviewSchedule
import com.pickme.schedule.domain.model.InterviewSearchSpec
import com.pickme.schedule.domain.model.InterviewUpdateSpec
import org.mapstruct.Mapper
import org.mapstruct.ObjectFactory

@Mapper(componentModel = "spring") // Spring Bean으로 등록
interface InterviewScheduleMapper {

    fun toDto(interviewSchedules: List<InterviewSchedule>): List<ScheduleDto>

    fun toDto(interviewSchedule: InterviewSchedule): ScheduleDto

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
