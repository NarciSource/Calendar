import { EventDetail } from "../dto";

/**
 * INotificationSender는 알림을 전송하기 위한 인터페이스입니다.
 */
export interface INotificationSender {
    dispatch(notification: EventDetail): Promise<void>;
}
