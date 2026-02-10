import { CalendarClient } from "./calendarClient";
import axios from "axios";

jest.mock("axios");

describe("CalendarClient", () => {
    const CALENDAR_API_URL = "http://mock-calendar-api.com";

    beforeEach(() => {
        process.env.CALENDAR_API_URL = CALENDAR_API_URL;
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it("올바른 baseURL과 헤더로 Axios 인스턴스를 생성", () => {
        const calendarClient = new CalendarClient();

        expect(calendarClient).toBeDefined();
        expect((axios.create as jest.Mock).mock.calls[0][0]).toEqual({
            baseURL: CALENDAR_API_URL,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
        });
    });

    it("Axios 인스턴스의 `get` 메서드를 반환", () => {
        const mockGet = jest.fn();
        (axios.create as jest.Mock).mockReturnValue({ get: mockGet });

        const calendarClient = new CalendarClient();

        expect(calendarClient.get).toBe(mockGet);
    });
});
