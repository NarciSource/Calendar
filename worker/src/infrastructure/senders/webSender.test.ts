import { Test, TestingModule } from "@nestjs/testing";

import { Schedule } from "application/dto";

import { OnesignalClient } from "../api/onesignalClient";
import { WebNotificationSender } from "./webSender";

describe("WebNotificationSender", () => {
    let webNotificationSender: WebNotificationSender;
    let onesignalClient: OnesignalClient;
    let schedule: Schedule;

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

        schedule = {
            id: "67890",
            company: { name: "Test Company", location: "Test Location" },
            date: new Date(),
            position: "Developer",
            category: "Engineering",
            description: "Test Description",
            clientId: "12345",
        };
    });

    it("알림 성공", async () => {
        const response = { data: "success" };
        jest.spyOn(onesignalClient, "post").mockResolvedValue(response);

        await webNotificationSender.dispatch(schedule);

        expect(onesignalClient.post).toHaveBeenCalledWith(null, {
            target_channel: "push",
            contents: {
                en: `${schedule.company.name}\n${schedule.description}\n${schedule.company.location}\n${new Date(schedule.date).toLocaleTimeString()}\n${schedule.position} ${schedule.category}`,
            },
            include_aliases: {
                external_id: [schedule.clientId],
            },
        });
    });

    it("알림 실패", async () => {
        const error = new Error("Failed to send notification");
        jest.spyOn(onesignalClient, "post").mockRejectedValue(error);

        console.error = jest.fn();

        await webNotificationSender.dispatch(schedule);

        expect(console.error).toHaveBeenCalledWith("Error sending notification:", error.message);
    });
});
