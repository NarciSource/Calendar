import { Inject } from "@nestjs/common";
import { IQueryHandler, QueryHandler } from "@nestjs/cqrs";

import NotificationEntity from "domain/model/entity";
import { NotificationRepository } from "application/port.out/NotificationRepository";
import GetQuery from "./Get.query";

@QueryHandler(GetQuery)
export default class GetHandler implements IQueryHandler<GetQuery> {
    constructor(
        @Inject(NotificationRepository)
        private readonly repository: NotificationRepository,
    ) {}

    /**
     * 특정 알림을 조회합니다.
     *
     * @param {GetQuery} query - 알림 조회 요청 쿼리 페이로드
     */
    async execute({ event_id }: GetQuery): Promise<NotificationEntity> {
        return this.repository.findById(event_id);
    }
}
