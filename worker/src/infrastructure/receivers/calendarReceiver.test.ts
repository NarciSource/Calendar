import { Test, TestingModule } from "@nestjs/testing";

import { EventDetail } from "application/dto";

import { CalendarEventReceiver } from "./calendarReceiver";
import { CalendarClient } from "../api";

describe("CalendarEventReceiver", () => {
    let receiver: CalendarEventReceiver;
    let client: CalendarClient;

    beforeEach(async () => {
        const module: TestingModule = await Test.createTestingModule({
            providers: [
                CalendarEventReceiver,
                {
                    provide: CalendarClient,
                    useValue: {
                        get: jest.fn(),
                    },
                },
            ],
        }).compile();

        receiver = module.get<CalendarEventReceiver>(CalendarEventReceiver);
        client = module.get<CalendarClient>(CalendarClient);
    });

    it("이벤트 세부 정보를 반환", async () => {
        const eventDetail: EventDetail = {
            interviewDetailId: "mock-id",
            company: {
                name: "mock-company",
                location: "mock-location",
            },
            interviewTime: new Date(),
            position: "mock-position",
            category: "mock-category",
            description: "mock-description",
            clientId: "mock-client-id",
        };
        (client.get as jest.Mock).mockResolvedValue({ status: 200, data: eventDetail });

        const result = await receiver.receive({ event_id: "test-event-id" });

        expect(result).toEqual(eventDetail);
        expect(client.get).toHaveBeenCalledWith(null, {
            params: { interviewDetailId: "test-event-id" },
        });
    });

    it("세부 정보가 없는 경우 오류", async () => {
        (client.get as jest.Mock).mockResolvedValue({ status: 200, data: null });

        await expect(receiver.receive({ event_id: "test-event-id" })).rejects.toThrow(
            "해당 이벤트 ID에 대한 상세 정보가 없습니다.",
        );
        expect(client.get).toHaveBeenCalledWith(null, {
            params: { interviewDetailId: "test-event-id" },
        });
    });

    it("호출 실패", async () => {
        (client.get as jest.Mock).mockResolvedValue({ status: 500, data: null });

        await expect(receiver.receive({ event_id: "test-event-id" })).rejects.toThrow(
            "캘린더 API 호출 실패",
        );
        expect(client.get).toHaveBeenCalledWith(null, {
            params: { interviewDetailId: "test-event-id" },
        });
    });
});
