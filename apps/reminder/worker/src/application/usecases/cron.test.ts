import { Test, TestingModule } from "@nestjs/testing";
import { SchedulerRegistry } from "@nestjs/schedule";
import { CronJob } from "cron";

import { WorkerCronService } from "./cron";
import { WorkerService } from "./service";

describe("WorkerCronService", () => {
    let workerCronService: WorkerCronService;
    let workerService: WorkerService;
    let schedulerRegistry: SchedulerRegistry;

    beforeEach(async () => {
        const module: TestingModule = await Test.createTestingModule({
            providers: [
                WorkerCronService,
                {
                    provide: WorkerService,
                    useValue: { start: jest.fn() },
                },
                {
                    provide: SchedulerRegistry,
                    useValue: {
                        addCronJob: jest.fn(),
                    },
                },
            ],
        }).compile();

        workerCronService = module.get<WorkerCronService>(WorkerCronService);
        workerService = module.get<WorkerService>(WorkerService);
        schedulerRegistry = module.get<SchedulerRegistry>(SchedulerRegistry);
    });

    it("서비스 정의", () => {
        expect(workerCronService).toBeDefined();
    });

    it("WorkerService 수행", async () => {
        const startSpy = jest.spyOn(workerService, "start");

        await workerCronService.handleCron();

        expect(startSpy).toHaveBeenCalled();
    });

    describe("크론 잡 스케줄", () => {
        it("기본 스케줄", () => {
            const addCronJobSpy = jest.spyOn(schedulerRegistry, "addCronJob");

            delete process.env.CRON_SCHEDULE; // 환경 변수를 삭제
            workerCronService.onModuleInit();

            expect(addCronJobSpy).toHaveBeenCalledWith("workerCronJob", expect.any(CronJob));
        });

        it("크론 작업을 생성하고 시작", () => {
            const addCronJobSpy = jest.spyOn(schedulerRegistry, "addCronJob");
            const cronJobSpy = jest.spyOn(CronJob.prototype, "start");

            process.env.CRON_SCHEDULE = "EVERY_MINUTE"; // 환경 변수를 모킹
            workerCronService.onModuleInit();

            expect(addCronJobSpy).toHaveBeenCalledWith("workerCronJob", expect.any(CronJob));
            expect(cronJobSpy).toHaveBeenCalled();
        });
    });
});
