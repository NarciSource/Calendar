import { CommandHandler, ICommandHandler } from "@nestjs/cqrs";
import { Inject } from "@nestjs/common";

import INotificationRepository from "application/port.out/INotificationRepository";
import DeleteCommand from "./Delete.command";

@CommandHandler(DeleteCommand)
export default class DeleteHandler implements ICommandHandler<DeleteCommand> {
    constructor(
        @Inject("INotificationRepository")
        private repository: INotificationRepository,
    ) {}

    /**
     * 특정 알림을 삭제합니다.
     *
     * @param {ReadRequestDTO} params - 읽기 요청 데이터 전송 객체
     * @returns 삭제 성공 여부
     */
    async execute({ event_id }: DeleteCommand): Promise<boolean> {
        return this.repository.deleteById(event_id);
    }
}
