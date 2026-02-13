import { Controller, Get } from "@nestjs/common";
import { ApiOperation, ApiResponse, ApiTags } from "@nestjs/swagger";
import { HealthCheck, HealthCheckService } from "@nestjs/terminus";

@Controller("/")
@ApiTags("Health Check")
export default class HealthCheckController {
    constructor(private health: HealthCheckService) {}

    @Get()
    @ApiOperation({ summary: "헬스 체크" })
    @ApiResponse({ status: 200, description: "성공적으로 조회" })
    @HealthCheck()
    check() {
        return this.health.check([]);
    }
}
