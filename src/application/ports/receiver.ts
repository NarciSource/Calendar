import { EventDetail } from "../dto";

/**
 * 이벤트 수신을 처리하기 위한 인터페이스입니다.
 */
export interface IEventReceiver {
    receive(params: any): Promise<EventDetail>;
}
