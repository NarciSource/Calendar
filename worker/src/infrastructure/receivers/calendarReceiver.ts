import { Injectable } from "@nestjs/common";

import { IEventReceiver } from "application/ports";
import { EventDetail } from "application/dto";

import { CalendarClient } from "../api";

/**
 * 캘린더 이벤트를 수신하는 클래스입니다.
 * 특정 이벤트 ID에 대한 상세 정보를 가져옵니다.
 */
@Injectable()
export class CalendarEventReceiver implements IEventReceiver {
    /**
     * CalendarEventReceiver 클래스의 생성자입니다.
     *
     * @param client CalendarClient 인스턴스
     */
    constructor(private readonly client: CalendarClient) {}

    /**
     * 주어진 이벤트 ID를 사용하여 캘린더 API에서 이벤트 상세 정보를 가져옵니다.
     *
     * @param {Object} params - 이벤트 ID를 포함하는 객체
     * @param {string} params.event_id - 조회할 이벤트의 ID
     * @returns {Promise<EventDetail>} 이벤트 상세 정보
     * @throws {Error} 이벤트 ID에 대한 상세 정보가 없거나 API 호출 실패 시 에러를 발생시킵니다.
     */
    async receive({ event_id }: { event_id: string }): Promise<EventDetail> {
        const { status, data: event_detail }: { status: number; data: EventDetail } =
            await this.client.get(null, {
                params: {
                    interviewDetailId: event_id,
                },
            });

        if (status === 200) {
            if (event_detail) {
                return event_detail;
            } else {
                throw new Error("해당 이벤트 ID에 대한 상세 정보가 없습니다.");
            }
        } else {
            throw new Error("캘린더 API 호출 실패");
        }
    }
}
