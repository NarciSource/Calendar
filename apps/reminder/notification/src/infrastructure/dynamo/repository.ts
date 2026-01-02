import { Injectable } from "@nestjs/common";
import { ModelType } from "dynamoose/dist/General";

import NotificationEntity, { NotificationStatus } from "domain/entity";
import INotificationRepository from "domain/repository";

import DynamoModel from "./model";

/**
 * DynamoDB를 사용하여 알림 데이터를 관리하는 저장소 클래스입니다.
 * `INotificationRepository` 인터페이스를 구현합니다.
 */
@Injectable()
export default class DynamoRepository implements INotificationRepository {
    /**
     * @private
     * @readonly
     * DynamoDB 모델을 나타내는 속성입니다.
     * 이 속성은 데이터베이스 작업을 수행하기 위해 사용됩니다.
     *
     * @type {ModelType<any>}
     */
    private readonly model: ModelType<any>;

    /**
     * DynamoDB 모델을 초기화합니다.
     * @param model DynamoDB 모델을 제공하는 `DynamoModel` 인스턴스
     */
    constructor(model: DynamoModel) {
        this.model = model.getModel();
    }

    /**
     * 알림 데이터를 생성합니다.
     * @param eventData 생성할 알림 데이터 (`NotificationEntity` 객체)
     * @returns 생성된 알림 데이터
     */
    async create(eventData: NotificationEntity) {
        return this.model.update({
            ...eventData,
            ttl: Math.floor(eventData.send_at.getTime() / 1000) + 60 * 60, // 1시간 후 삭제
        });
    }

    /**
     * ID를 사용하여 알림 데이터를 조회합니다.
     * @param event_id 조회할 알림 데이터의 ID
     * @returns 조회된 알림 데이터
     */
    async findById(event_id: string) {
        return this.model.get(event_id);
    }

    /**
     * 예약된 시간과 상태를 기준으로 알림 데이터를 조회합니다.
     * @param start_time 조회를 시작할 시간
     * @param _end_time 조회를 종료할 시간 (현재 사용되지 않음)
     * @param status 조회할 알림의 상태 (`NotificationStatus`)
     * @returns 조회된 알림 데이터 목록
     */
    async findByReservationTime(start_time: Date, _end_time: Date, status: NotificationStatus) {
        const start_ts = new Date(start_time).getTime();

        return this.model
            .query("send_at")
            .eq(start_ts)
            .where("status")
            .eq(status)
            .using("send_at-status-index")
            .exec();
    }

    /**
     * ID를 사용하여 알림 데이터를 삭제합니다.
     * @param event_id 삭제할 알림 데이터의 ID
     * @returns 삭제 결과
     */
    async deleteById(event_id: string) {
        return this.model.delete(event_id);
    }
}
