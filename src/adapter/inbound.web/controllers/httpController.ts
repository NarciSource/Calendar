import { Body, Controller, Delete, Get, Param, Patch, Post, Put, Query } from "@nestjs/common";
import {
    ApiBearerAuth,
    ApiExtraModels,
    ApiOperation,
    ApiParam,
    ApiResponse,
    ApiTags,
} from "@nestjs/swagger";

import NotificationService from "application/service";

import { CreateRequestDTO, ReadRequestDTO, UpdateRequestDTO, ParametersDTO } from "../dtos";

@Controller("/reminder/")
@ApiTags("HTTP API")
@ApiBearerAuth()
@ApiResponse({ status: 401, description: "권한 없음" })
@ApiResponse({ status: 400, description: "입력 값 오류" })
export default class NotificationHttpController {
    constructor(private readonly service: NotificationService) {}

    @Post()
    @ApiOperation({ summary: "알림 등록" })
    @ApiResponse({ status: 201, description: "성공적으로 등록" })
    async create(@Body() dto: CreateRequestDTO) {
        return this.service.register(dto);
    }

    @Get(":event_id")
    @ApiOperation({ summary: "알림 조회" })
    @ApiResponse({ status: 200, description: "성공적으로 조회" })
    async read(@Param() dto: ReadRequestDTO) {
        return this.service.get(dto);
    }

    @Get()
    @ApiOperation({ summary: "알림 옵션 조회" })
    @ApiResponse({ status: 200, description: "성공적으로 조회" })
    @ApiExtraModels(ParametersDTO)
    async readByOptions(@Query() query: ParametersDTO) {
        return this.service.getFilteredList(query);
    }

    @Put(":event_id")
    @ApiOperation({ summary: "알림 수정" })
    @ApiResponse({ status: 200, description: "성공적으로 수정" })
    async update(@Param() paramDTO: ReadRequestDTO, @Body() bodyDTO: CreateRequestDTO) {
        return this.service.register({ ...paramDTO, ...bodyDTO });
    }

    @Patch(":event_id")
    @ApiOperation({ summary: "알림 일부 수정" })
    @ApiResponse({ status: 200, description: "성공적으로 수정" })
    async updatePartial(@Param() paramDTO: ReadRequestDTO, @Body() bodyDTO: UpdateRequestDTO) {
        return this.service.update(paramDTO, bodyDTO);
    }

    @Delete(":event_id")
    @ApiOperation({ summary: "알림 삭제" })
    @ApiResponse({ status: 200, description: "성공적으로 수정" })
    @ApiParam({ name: "event_id", type: ReadRequestDTO })
    async delete(@Param() dto: ReadRequestDTO) {
        return this.service.delete(dto);
    }
}
