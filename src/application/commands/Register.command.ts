import { NotificationStatus } from "domain/model/entity";

export default class RegisterCommand {
    constructor(
        public readonly event_id: string,
        public readonly send_at: Date,
        public readonly status: NotificationStatus,
    ) {}
}
