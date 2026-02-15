import { Inject } from "@nestjs/common";
import { CommandHandler, ICommandHandler } from "@nestjs/cqrs";

import NotificationEntity from "domain/model/entity";
import ReplaceCommand from "./Replace.command";
import { NotificationRepository } from "application/port.out/NotificationRepository";

@CommandHandler(ReplaceCommand)
export default class ReplaceHandler implements ICommandHandler<ReplaceCommand> {
    constructor(
        @Inject(NotificationRepository)
        private readonly repository: NotificationRepository,
    ) {}

    /**
     * 기존 알림을 완전히 대체합니다.
     *
     * @param {ReplaceCommand} command - 대체 요청 커맨드 페이로드
     * @returns 대체된 알림 엔티티
     * @throws Error 엔티티가 존재하지 않을 경우
     */
    async execute({ event_id, send_at, status }: ReplaceCommand) {
        const entity = new NotificationEntity(event_id, send_at, status); // 도메인 객체 생성

        return this.repository.replace(event_id, entity);
    }
}
