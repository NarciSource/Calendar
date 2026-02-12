import { Controller, Get, Patch } from "@nestjs/common";
import { CommandBus, QueryBus } from "@nestjs/cqrs";
import { MessagePattern } from "@nestjs/microservices";
import { ApiBody, ApiOperation, ApiTags } from "@nestjs/swagger";

import { UpdateCommand } from "application/commands";
import { FindQuery } from "application/queries";
import { PayloadEX } from "../decorators";
import { ParametersDTO, UpdateRequestDTO } from "../dtos";

@Controller("/reminder/message/")
@ApiTags("TCP API")
export default class NotificationsMessageController {
    constructor(
        private readonly query_bus: QueryBus,
        private readonly command_bus: CommandBus,
    ) {}

    @Get("docs")
    @ApiOperation({
        summary: "알림 옵션 조회",
        description: "이 API는 메시지 브로커를 통해 동작하며, HTTP 요청으로는 사용되지 않습니다.",
    })
    @ApiBody({ type: ParametersDTO })
    @MessagePattern({ cmd: "readByOptions" })
    async readByOptions(@PayloadEX(ParametersDTO) payload: ParametersDTO) {
        const query = new FindQuery(payload.start_time, payload.end_time, payload.status);

        return this.query_bus.execute(query);
    }

    @Patch("docs")
    @ApiOperation({
        summary: "알림 부분 수정",
        description: "이 API는 메시지 브로커를 통해 동작하며, HTTP 요청으로는 사용되지 않습니다.",
    })
    @ApiBody({ type: UpdateRequestDTO })
    @MessagePattern({ cmd: "updatePartial" })
    async updatePartial(@PayloadEX(UpdateRequestDTO) payload: UpdateRequestDTO) {
        const command = new UpdateCommand(payload.event_id, payload.send_at, payload.status);

        return this.command_bus.execute(command);
    }
}
