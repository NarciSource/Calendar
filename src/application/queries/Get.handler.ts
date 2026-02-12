import { Inject } from "@nestjs/common";
import { IQueryHandler, QueryHandler } from "@nestjs/cqrs";

import NotificationEntity from "domain/model/entity";
import INotificationRepository from "application/port.out/INotificationRepository";
import GetQuery from "./Get.query";

@QueryHandler(GetQuery)
export default class GetHandler implements IQueryHandler<GetQuery> {
    constructor(
        @Inject("INotificationRepository")
        private readonly repository: INotificationRepository,
    ) {}

    /**
     * 특정 알림을 조회합니다.
     *
     * @param {ReadRequestDTO} params - 읽기 요청 데이터 전송 객체
     * @returns 조회된 알림 엔티티
     */
    async execute({ event_id }: GetQuery): Promise<NotificationEntity> {
        return this.repository.findById(event_id);
    }
}
