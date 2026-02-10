import NotificationEntity, { NotificationStatus } from "./entity";

/**
 * 알림 저장소 인터페이스입니다. 알림 데이터를 생성, 조회, 삭제하는 메서드를 정의합니다.
 */
export default interface INotificationRepository {
    /**
     * 새로운 알림 엔티티를 생성합니다.
     *
     * @param entity - 생성할 알림 엔티티
     * @returns 생성된 알림 엔티티를 반환합니다.
     */
    create(entity: NotificationEntity): Promise<NotificationEntity>;

    /**
     * 주어진 이벤트 ID를 기반으로 알림 엔티티를 조회합니다.
     *
     * @param event_id - 조회할 알림의 이벤트 ID
     * @returns 조회된 알림 엔티티를 반환합니다.
     */
    findById(event_id: string): Promise<NotificationEntity>;

    /**
     * 예약 시간 범위와 상태를 기준으로 알림 엔티티 목록을 조회합니다.
     *
     * @param start_time - 조회할 시작 시간
     * @param end_time - 조회할 종료 시간
     * @param status - 조회할 알림 상태
     * @returns 조건에 맞는 알림 엔티티 배열을 반환합니다.
     */
    findByReservationTime(
        start_time: Date,
        end_time: Date,
        status: NotificationStatus,
    ): Promise<NotificationEntity[]>;

    /**
     * 주어진 이벤트 ID를 기반으로 알림 엔티티를 삭제합니다.
     *
     * @param event_id - 삭제할 알림의 이벤트 ID
     * @returns 삭제 성공 여부를 반환합니다.
     */
    deleteById(event_id: string): Promise<boolean>;
}
