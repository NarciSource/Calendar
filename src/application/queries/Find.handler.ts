import { Inject } from "@nestjs/common";
import { IQueryHandler, QueryHandler } from "@nestjs/cqrs";

import NotificationEntity from "domain/model/entity";
import { NotificationRepository } from "application/port.out/NotificationRepository";
import FindQuery from "./Find.query";

@QueryHandler(FindQuery)
export default class FindHandler implements IQueryHandler<FindQuery> {
    constructor(
        @Inject(NotificationRepository)
        private readonly repository: NotificationRepository,
    ) {}

    /**
     * 주어진 필터 조건에 따라 알림 엔티티 목록을 가져옵니다.
     *
     * @param {FindQuery} query - 알림 검색 요청 쿼리 페이로드
     * @returns 필터링된 알림 엔티티 배열
     */
    async execute({ start_time, end_time, status }: FindQuery): Promise<NotificationEntity[]> {
        return this.repository.findByReservationTime(start_time, end_time, status);
    }
}
