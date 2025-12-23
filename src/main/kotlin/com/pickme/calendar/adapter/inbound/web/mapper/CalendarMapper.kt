package com.pickme.calendar.adapter.inbound.web.mapper

import com.pickme.calendar.adapter.inbound.web.dto.request.GetInterviewDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PostInterviewDto
import com.pickme.calendar.adapter.inbound.web.dto.request.PutInterviewDto
import com.pickme.calendar.adapter.inbound.web.dto.request.payload.CompanyDto
import com.pickme.calendar.adapter.inbound.web.dto.response.CalendarDto
import com.pickme.calendar.domain.model.Calendar
import com.pickme.calendar.domain.model.InterviewDetail
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.ObjectFactory

@Mapper(componentModel = "spring") // Spring Bean으로 등록
interface CalendarMapper {
    fun toDto(calendar: Calendar): CalendarDto

    fun toDto(interviewDetails: List<InterviewDetail>): List<GetInterviewDto>

    fun toDto(interviewDetail: InterviewDetail): GetInterviewDto

    fun toEntity(dto: CompanyDto): InterviewDetail.Company

    fun toEntity(putInterviewDto: PutInterviewDto, @MappingTarget interviewDetail: InterviewDetail)

    @ObjectFactory
    fun toEntity(dto: PostInterviewDto): InterviewDetail {
        return InterviewDetail(
            company = toEntity(dto.company),
            interviewTime = dto.interviewTime,
            position = dto.position,
            category = dto.category,
            description = dto.description
        )
    }
}
