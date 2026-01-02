import { Test, TestingModule } from "@nestjs/testing";

import { EventDetail } from "application/dto";

import { OnesignalClient } from "../api/onesignalClient";
import { WebNotificationSender } from "./webSender";

describe("WebNotificationSender", () => {
    let webNotificationSender: WebNotificationSender;
    let onesignalClient: OnesignalClient;
    let eventDetail: EventDetail;

    beforeEach(async () => {
        const module: TestingModule = await Test.createTestingModule({
            providers: [
                WebNotificationSender,
                {
                    provide: OnesignalClient,
                    useValue: {
                        post: jest.fn(),
                    },
                },
            ],
        }).compile();

        webNotificationSender = module.get<WebNotificationSender>(WebNotificationSender);
        onesignalClient = module.get<OnesignalClient>(OnesignalClient);

        eventDetail = {
            interviewDetailId: "67890",
            company: { name: "Test Company", location: "Test Location" },
            interviewTime: new Date(),
            position: "Developer",
            category: "Engineering",
            description: "Test Description",
            clientId: "12345",
        };
    });

    it("알림 성공", async () => {
        const response = { data: "success" };
        jest.spyOn(onesignalClient, "post").mockResolvedValue(response);

        await webNotificationSender.dispatch(eventDetail);

        expect(onesignalClient.post).toHaveBeenCalledWith(null, {
            target_channel: "push",
            contents: {
                en: `${eventDetail.company.name}\n${eventDetail.description}\n${eventDetail.company.location}\n${new Date(eventDetail.interviewTime).toLocaleTimeString()}\n${eventDetail.position} ${eventDetail.category}`,
            },
            include_aliases: {
                external_id: [eventDetail.clientId],
            },
        });
    });

    it("알림 실패", async () => {
        const error = new Error("Failed to send notification");
        jest.spyOn(onesignalClient, "post").mockRejectedValue(error);

        console.error = jest.fn();

        await webNotificationSender.dispatch(eventDetail);

        expect(console.error).toHaveBeenCalledWith("Error sending notification:", error.message);
    });
});
