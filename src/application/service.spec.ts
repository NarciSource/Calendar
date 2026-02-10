import { Test, TestingModule } from "@nestjs/testing";

import INotificationRepository from "domain/repository";
import NotificationEntity, { NotificationStatus } from "domain/entity";

import { RegisterRequestDTO, ReadRequestDTO, UpdateRequestDTO, OptionsDTO } from "./dto";
import NotificationService from "./service";

describe("NotificationService", () => {
    let service: NotificationService;
    let repository: INotificationRepository;

    beforeEach(async () => {
        const module: TestingModule = await Test.createTestingModule({
            providers: [
                NotificationService,
                {
                    provide: "INotificationRepository", // 테스트용 레포지토리 설정
                    useValue: {
                        create: jest.fn(),
                        findById: jest.fn(),
                        findByReservationTime: jest.fn(),
                        deleteById: jest.fn(),
                    },
                },
            ],
        }).compile();

        service = module.get<NotificationService>(NotificationService);
        repository = module.get<INotificationRepository>("INotificationRepository");
    });

    it("서비스 정의", () => {
        expect(service).toBeDefined();
    });

    describe("register", () => {
        it("새로운 알림 엔터티를 생성", async () => {
            const dto: RegisterRequestDTO = {
                event_id: "1",
                send_at: new Date(),
                status: NotificationStatus.Pending,
            };
            const entity = new NotificationEntity(dto.event_id, dto.send_at, dto.status);

            jest.spyOn(repository, "create").mockResolvedValue(entity);

            expect(await service.register(dto)).toEqual(entity);
            expect(repository.create).toHaveBeenCalledWith(entity);
        });
    });

    describe("update", () => {
        it("알림 엔터티를 업데이트", async () => {
            const paramDTO: ReadRequestDTO = { event_id: "1" };
            const bodyDTO: UpdateRequestDTO = {
                send_at: new Date(),
                status: NotificationStatus.Sent,
            };
            const existingEntity = new NotificationEntity(
                paramDTO.event_id,
                new Date(),
                NotificationStatus.Pending,
            );

            jest.spyOn(service, "get").mockResolvedValue(existingEntity);
            jest.spyOn(service, "register").mockResolvedValue(existingEntity);

            expect(await service.update(paramDTO, bodyDTO)).toEqual(existingEntity);
            expect(service.get).toHaveBeenCalledWith(paramDTO);
            expect(service.register).toHaveBeenCalledWith({
                ...existingEntity,
                ...paramDTO,
                ...bodyDTO,
            });
        });

        it("엔터티를 찾을 수 없으면 오류를 발생", async () => {
            const paramDTO: ReadRequestDTO = { event_id: "1" };
            const bodyDTO: UpdateRequestDTO = {
                send_at: new Date(),
                status: NotificationStatus.Sent,
            };

            jest.spyOn(service, "get").mockResolvedValue(null);

            await expect(service.update(paramDTO, bodyDTO)).rejects.toThrow("Entity not found.");
        });
    });

    describe("get", () => {
        it("id로 알림 엔터티를 반환", async () => {
            const dto: ReadRequestDTO = { event_id: "1" };
            const entity = new NotificationEntity(
                dto.event_id,
                new Date(),
                NotificationStatus.Pending,
            );

            jest.spyOn(repository, "findById").mockResolvedValue(entity);

            expect(await service.get(dto)).toEqual(entity);
            expect(repository.findById).toHaveBeenCalledWith(dto.event_id);
        });
    });

    describe("getFilteredList", () => {
        it("알림 엔터티 목록을 반환", async () => {
            const dto: OptionsDTO = {
                start_time: new Date(),
                end_time: new Date(),
                status: NotificationStatus.Pending,
            };
            const entities = [new NotificationEntity("1", new Date(), NotificationStatus.Pending)];

            jest.spyOn(repository, "findByReservationTime").mockResolvedValue(entities);

            expect(await service.getFilteredList(dto)).toEqual(entities);
            expect(repository.findByReservationTime).toHaveBeenCalledWith(
                dto.start_time,
                dto.end_time,
                dto.status,
            );
        });
    });

    describe("delete", () => {
        it("ID로 알림 엔터티를 삭제", async () => {
            const dto: ReadRequestDTO = { event_id: "1" };

            jest.spyOn(repository, "deleteById").mockResolvedValue(true);

            expect(await service.delete(dto)).toEqual(true);
            expect(repository.deleteById).toHaveBeenCalledWith(dto.event_id);
        });
    });
});
