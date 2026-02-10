import { Test, TestingModule } from "@nestjs/testing";

import NotificationEntity, { NotificationStatus } from "domain/entity";

import NotificationService from "application/service";

import { ParametersDTO, UpdateRequestDTO } from "../dtos";
import NotificationsMessageController from "./messageController";

describe("NotificationsMessageController", () => {
    let controller: NotificationsMessageController;
    let service: NotificationService;

    beforeEach(async () => {
        const module: TestingModule = await Test.createTestingModule({
            controllers: [NotificationsMessageController],
            providers: [
                {
                    provide: NotificationService,
                    useValue: {
                        getFilteredList: jest.fn(),
                        update: jest.fn(),
                    },
                },
            ],
        }).compile();

        controller = module.get<NotificationsMessageController>(NotificationsMessageController);
        service = module.get<NotificationService>(NotificationService);
    });

    it("서비스 정의", () => {
        expect(controller).toBeDefined();
    });

    it("알림 옵션 조회", async () => {
        const payload: ParametersDTO = {}; // mock data
        const result = []; // mock result

        jest.spyOn(service, "getFilteredList").mockResolvedValue(result);

        expect(await controller.readByOptions(payload)).toBe(result);
        expect(service.getFilteredList).toHaveBeenCalledWith(payload);
    });

    it("알림 부분 수정", async () => {
        const payload: UpdateRequestDTO = { event_id: "123" };
        const result = new NotificationEntity("123", new Date(), NotificationStatus.Pending);

        jest.spyOn(service, "update").mockResolvedValue(result);

        expect(await controller.updatePartial(payload)).toBe(result);
        expect(service.update).toHaveBeenCalledWith({ event_id: "123" }, {});
    });
});
