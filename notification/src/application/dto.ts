import { NotificationStatus } from "domain/entity";

export { NotificationStatus };

/**
 * 알림 등록 요청을 나타내는 데이터 전송 객체입니다.
 *
 * @property event_id - 알림과 연관된 이벤트의 고유 식별자입니다.
 * @property send_at - 알림이 전송될 날짜 및 시간입니다.
 * @property status - 알림의 상태를 나타내며, 선택적으로 설정할 수 있습니다.
 */
export interface RegisterRequestDTO {
    event_id: string;
    send_at: Date;
    status?: NotificationStatus;
}

/**
 * 알림 업데이트 요청을 나타내는 데이터 전송 객체입니다.
 *
 * @property send_at - 알림이 전송될 예정 시간입니다. 선택적으로 설정할 수 있습니다.
 * @property status - 알림의 상태를 나타냅니다. 선택적으로 설정할 수 있습니다.
 */
export interface UpdateRequestDTO {
    send_at?: Date;
    status?: NotificationStatus;
}

/**
 * 읽기 요청을 나타내는 데이터 전송 객체입니다.
 *
 * @property event_id - 알림과 연관된 이벤트의 고유 식별자입니다.
 */
export interface ReadRequestDTO {
    event_id: string;
}

/**
 * 알림 옵션을 정의하는 데이터 전송 객체입니다.
 *
 * @property start_time - 알림의 시작 시간을 나타내는 선택적 Date 객체입니다.
 * @property end_time - 알림의 종료 시간을 나타내는 선택적 Date 객체입니다.
 * @property status - 알림의 상태를 나타내는 선택적 NotificationStatus 값입니다.
 */
export interface OptionsDTO {
    start_time?: Date;
    end_time?: Date;
    status?: NotificationStatus;
}
