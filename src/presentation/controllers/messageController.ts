import { Controller, Get, Patch } from "@nestjs/common";
import { ApiOperation, ApiTags, ApiBody } from "@nestjs/swagger";
import { MessagePattern } from "@nestjs/microservices";

import NotificationService from "application/service";

import { ParametersDTO, UpdateRequestDTO } from "../dtos";
import { PayloadEX } from "utility/decorators";

@Controller("/reminder/message/")
@ApiTags("TCP API")
export default class NotificationsMessageController {
    constructor(private readonly service: NotificationService) {}

    @Get("docs")
    @ApiOperation({
        summary: "알림 옵션 조회",
        description: "이 API는 메시지 브로커를 통해 동작하며, HTTP 요청으로는 사용되지 않습니다.",
    })
    @ApiBody({ type: ParametersDTO })
    @MessagePattern({ cmd: "readByOptions" })
    async readByOptions(@PayloadEX(ParametersDTO) payload: ParametersDTO) {
        return this.service.getFilteredList(payload);
    }

    @Patch("docs")
    @ApiOperation({
        summary: "알림 부분 수정",
        description: "이 API는 메시지 브로커를 통해 동작하며, HTTP 요청으로는 사용되지 않습니다.",
    })
    @ApiBody({ type: UpdateRequestDTO })
    @MessagePattern({ cmd: "updatePartial" })
    async updatePartial(@PayloadEX(UpdateRequestDTO) payload: UpdateRequestDTO) {
        const { event_id, ...args } = payload;
        return this.service.update({ event_id }, args);
    }
}
