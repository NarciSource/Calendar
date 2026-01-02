import { ExecutionContext, CallHandler, UnauthorizedException } from "@nestjs/common";
import { of } from "rxjs";

process.env.COGNITO_USER_POOL_ID = "dummy-pool-id";
process.env.COGNITO_CLIENT_ID = "dummy-client-id";

import { JwtInterceptor } from "./jwtInterceptor";
import verifier from "./verifier";

jest.mock("aws-jwt-verify", () => {
    return {
        CognitoJwtVerifier: {
            create: jest.fn().mockReturnValue({
                verify: jest.fn().mockRejectedValue(new Error("Invalid token")),
            }),
        },
    };
});

describe("JwtInterceptor", () => {
    let interceptor: JwtInterceptor;
    let mockExecutionContext: Partial<ExecutionContext>;
    let mockCallHandler: Partial<CallHandler>;

    beforeEach(() => {
        interceptor = new JwtInterceptor();

        mockExecutionContext = {
            switchToHttp: jest.fn().mockReturnValue({
                getRequest: jest.fn().mockReturnValue({
                    headers: {},
                }),
            }),
            getClass: jest.fn(),
        };

        mockCallHandler = {
            handle: jest.fn().mockReturnValue(of(null)),
        };
    });

    it("헬스체크 컨트롤러에서는 인터셉터를 적용하지 않음", async () => {
        mockExecutionContext.getClass = jest
            .fn()
            .mockReturnValue({ name: "HealthCheckController" });

        const result = await interceptor.intercept(
            mockExecutionContext as ExecutionContext,
            mockCallHandler as CallHandler,
        );

        expect(result).toBeDefined();
        expect(mockCallHandler.handle).toHaveBeenCalled();
    });

    it("JWT 토큰이 없으면 UnauthorizedException을 발생", async () => {
        mockExecutionContext.getClass = jest.fn().mockReturnValue({ name: "SomeController" });

        await expect(
            interceptor.intercept(
                mockExecutionContext as ExecutionContext,
                mockCallHandler as CallHandler,
            ),
        ).rejects.toThrow(UnauthorizedException);
    });

    it("유효하지 않은 JWT 토큰이면 UnauthorizedException을 발생", async () => {
        mockExecutionContext.getClass = jest.fn().mockReturnValue({ name: "SomeController" });
        mockExecutionContext.switchToHttp = jest.fn().mockReturnValue({
            getRequest: jest.fn().mockReturnValue({
                headers: { authorization: "Bearer invalidToken" },
            }),
        });

        (verifier.verify as jest.Mock).mockRejectedValue(new Error("Invalid token"));

        await expect(
            interceptor.intercept(
                mockExecutionContext as ExecutionContext,
                mockCallHandler as CallHandler,
            ),
        ).rejects.toThrow(UnauthorizedException);
    });

    it("유효한 JWT 토큰이면 요청 객체에 사용자 정보를 추가", async () => {
        const mockUser = { id: "123", name: "Test User" };
        mockExecutionContext.getClass = jest.fn().mockReturnValue({ name: "SomeController" });
        mockExecutionContext.switchToHttp = jest.fn().mockReturnValue({
            getRequest: jest.fn().mockReturnValue({
                headers: { authorization: "Bearer validToken" },
            }),
        });

        (verifier.verify as jest.Mock).mockResolvedValue(mockUser);

        const result = await interceptor.intercept(
            mockExecutionContext as ExecutionContext,
            mockCallHandler as CallHandler,
        );

        const request = mockExecutionContext.switchToHttp().getRequest();
        expect(request.user).toEqual(mockUser);
        expect(result).toBeDefined();
        expect(mockCallHandler.handle).toHaveBeenCalled();
    });
});
