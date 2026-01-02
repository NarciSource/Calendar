import { Controller, Get, HttpCode } from "@nestjs/common";
import { ApiOperation, ApiResponse, ApiTags } from "@nestjs/swagger";

@Controller("/")
@ApiTags("Health Check")
export default class HealthCheckController {
    @Get()
    @HttpCode(200)
    @ApiOperation({ summary: "헬스 체크" })
    @ApiResponse({ status: 200, description: "성공적으로 조회" })
    async healthCheck() {
        return "OK";
    }
}
