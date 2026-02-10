import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { ScheduleModule } from "@nestjs/schedule";

import { WorkerService } from "application/usecases/service";
import { WorkerCronService } from "application/usecases/cron";

import { WorkerClientImpl } from "infrastructure/clientImpl";
import { WebNotificationSender } from "infrastructure/senders/webSender";
import { CalendarEventReceiver } from "infrastructure/receivers/calendarReceiver";
import { OnesignalClient, CalendarClient } from "infrastructure/api";

/**
 * @module WorkerModule
 *
 * 이 모듈은 작업자(Worker) 서비스와 관련된 기능을 제공합니다.
 * 스케줄링, 환경설정, 알림 발송, 캘린더 이벤트 수신 등 다양한 기능을 포함합니다.
 *
 * - `imports`: 서비스와 인프라스트럭처를 정의합니다.
 *   - `ScheduleModule`: 작업 스케줄링을 위한 모듈입니다.
 *   - `ConfigModule`: 환경설정을 전역적으로 관리하기 위한 모듈입니다.
 *
 * - `providers`: 서비스와 인프라스트럭처를 정의합니다.
 *   - `WorkerService`: 작업자 서비스의 핵심 유즈케이스를 처리합니다.
 *   - `WorkerCronService`: 작업자 크론 작업을 처리합니다.
 *   - `IWorkerClient`: Notification 마이크로서비스와의 TCP 통신을 처리하는 인프라입니다.
 *   - `INotificationSender`: 알림 발송을 처리하는 인프라입니다.
 *   - `IEventReceiver`: 캘린더 이벤트 수신을 처리하는 인프라입니다.
 *   - `OnesignalClient`: OneSignal API와의 통신을 처리합니다.
 *   - `CalendarClient`: 캘린더 API와의 통신을 처리합니다.
 */
@Module({
    imports: [
        ScheduleModule.forRoot(),
        ConfigModule.forRoot({
            isGlobal: true,
            envFilePath: [".env.local", ".env"],
        }),
    ],
    providers: [
        /** 유즈케이스 */
        WorkerService,
        WorkerCronService,

        /** 인프라스트럭처 */
        {
            // Notification 마이크로서비스 TCP 송발신 인프라
            provide: "IWorkerClient", // 인터페이스 제공
            useClass: WorkerClientImpl, // 구현체 연결
        },
        {
            // 알림 발송하는 인프라
            provide: "INotificationSender",
            useClass: WebNotificationSender,
        },
        {
            // 캘린더 이벤트 수신하는 인프라
            provide: "IEventReceiver",
            useClass: CalendarEventReceiver,
        },

        /** API 래핑 */
        OnesignalClient,
        CalendarClient,
    ],
})
export class WorkerModule {}
