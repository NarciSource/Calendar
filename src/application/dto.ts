/**
 * 알림 상태를 나타내는 열거형입니다.
 *
 * - `Pending`: 알림이 아직 전송되지 않은 상태.
 * - `Sent`: 알림이 성공적으로 전송된 상태.
 * - `Failed`: 알림 전송이 실패한 상태.
 */
export enum NotificationStatus {
    Pending = "Pending",
    Sent = "Sent",
    Failed = "Failed",
}

/**
 * 알림 엔티티를 나타내는 인터페이스입니다.
 *
 * @property event_id 알림이 할당된 이벤트의 ID입니다.
 * @property send_at 알림 발송 시간입니다.
 * @property status 알림의 상태를 나타냅니다.
 */
export interface NotificationEntity {
    event_id: string;
    send_at: Date;
    status: NotificationStatus;
}

/**
 * 알림 서비스의 매개변수를 정의하는 데이터 전송 객체입니다.
 *
 * @property start_time - 알림의 시작 시간을 나타내는 선택적 `Date` 객체입니다.
 * @property end_time - 알림의 종료 시간을 나타내는 선택적 `Date` 객체입니다.
 * @property status - 알림의 상태를 나타내는 선택적 `NotificationStatus` 값입니다.
 */
export interface ParametersDTO {
    start_time?: Date;
    end_time?: Date;
    status?: NotificationStatus;
}

/**
 * 이벤트 세부 정보를 나타내는 인터페이스입니다.
 *
 * @interface EventDetail
 * @property interviewDetailId - 인터뷰 세부 정보의 고유 식별자입니다.
 * @property company - 회사 정보를 포함하는 객체입니다.
 * @property company.name - 회사 이름입니다.
 * @property company.location - 회사 위치입니다.
 * @property interviewTime - 인터뷰가 예정된 시간입니다.
 * @property position - 지원하는 직책입니다.
 * @property category - 이벤트의 카테고리입니다.
 * @property description - 이벤트에 대한 설명입니다.
 * @property clientId - 클라이언트의 고유 식별자입니다.
 */
export interface EventDetail {
    interviewDetailId: string;
    company: {
        name: string;
        location: string;
    };
    interviewTime: Date;
    position: string;
    category: string;
    description: string;
    clientId: string;
}
