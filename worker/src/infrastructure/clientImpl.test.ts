import { Test, TestingModule } from "@nestjs/testing";
import { ClientTCP } from "@nestjs/microservices";

import { WorkerClientImpl } from "./clientImpl";

jest.mock("@nestjs/microservices", () => ({
    ClientTCP: jest.fn().mockImplementation(() => ({
        connect: jest.fn(),
        send: jest.fn(),
        handleError: jest.fn(),
    })),
}));

describe("WorkerClientImpl", () => {
    let workerClient: WorkerClientImpl;
    let clientTCPMock: jest.Mocked<ClientTCP>;

    beforeEach(async () => {
        const module: TestingModule = await Test.createTestingModule({
            providers: [WorkerClientImpl],
        }).compile();

        workerClient = module.get<WorkerClientImpl>(WorkerClientImpl);
        clientTCPMock = workerClient["client"] as jest.Mocked<ClientTCP>;
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    describe("constructor", () => {
        it("handleError가 트리거될 때 오류를 로깅", () => {
            const consoleErrorSpy = jest.spyOn(console, "error").mockImplementation();
            const error = new Error("Test error");

            clientTCPMock.handleError(error);

            expect(consoleErrorSpy).toHaveBeenCalledWith("Connection error", error);
            expect(workerClient["isConnected"]).toBe(false);

            consoleErrorSpy.mockRestore();
        });
    });

    describe("ensureConnected", () => {
        it("연결되지 않은 경우 재연결을 시도", async () => {
            jest.useFakeTimers();
            const connectClientSpy = jest
                .spyOn(workerClient, "connectClient")
                .mockResolvedValue(true);

            const promise = workerClient.ensureConnected();
            jest.advanceTimersByTime(1000 * 60 * 10);
            await promise;

            expect(connectClientSpy).toHaveBeenCalled();

            jest.useRealTimers();
        });
    });

    describe("connectClient", () => {
        it("연결 시도", async () => {
            clientTCPMock.connect.mockResolvedValueOnce(undefined);

            const result = await workerClient.connectClient();

            expect(clientTCPMock.connect).toHaveBeenCalled();
            expect(result).toBe(true);
        });

        it("연결 실패 시 오류를 로깅하고 false를 반환해야 함", async () => {
            const consoleErrorSpy = jest.spyOn(console, "error").mockImplementation();
            clientTCPMock.connect.mockRejectedValueOnce(new Error("Connection failed"));

            const result = await workerClient.connectClient();

            expect(consoleErrorSpy).toHaveBeenCalledWith(
                "Connection failed. Retrying in 10 minutes...",
                expect.any(Error),
            );
            expect(result).toBe(false);

            consoleErrorSpy.mockRestore();
        });
    });
});
