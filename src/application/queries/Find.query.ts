import { NotificationStatus } from "domain/model/entity";

export default class FindQuery {
    constructor(
        public readonly start_time?: Date,
        public readonly end_time?: Date,
        public readonly status?: NotificationStatus,
    ) {}
}
