import { Inject } from "@nestjs/common";
import { IQueryHandler, QueryHandler } from "@nestjs/cqrs";

import NotificationEntity from "domain/model/entity";
import INotificationRepository from "application/port.out/INotificationRepository";
import FindQuery from "./Find.query";

@QueryHandler(FindQuery)
export default class FindHandler implements IQueryHandler<FindQuery> {
    constructor(
        @Inject("INotificationRepository")
        private readonly repository: INotificationRepository,
    ) {}

    /**
     * 주어진 필터 조건에 따라 알림 엔티티 목록을 가져옵니다.
     *
     * @param {OptionsDTO} params - 필터링에 사용할 옵션 객체
     * @returns {Promise<NotificationEntity[]>} 필터링된 알림 엔티티 배열을 반환합니다.
     */
    async execute({ start_time, end_time, status }: FindQuery): Promise<NotificationEntity[]> {
        return this.repository.findByReservationTime(start_time, end_time, status);
    }
}
