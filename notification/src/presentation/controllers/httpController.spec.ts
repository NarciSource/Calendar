import { Test, TestingModule } from "@nestjs/testing";
import { INestApplication } from "@nestjs/common";
import * as request from "supertest";

import { NotificationStatus } from "domain/entity";

import NotificationService from "application/service";

import { CreateRequestDTO, ParametersDTO, UpdateRequestDTO } from "../dtos";
import NotificationController from "./httpController";

describe("NotificationController", () => {
    let app: INestApplication;
    let service: NotificationService;

    beforeAll(async () => {
        const moduleFixture: TestingModule = await Test.createTestingModule({
            controllers: [NotificationController],
            providers: [
                {
                    provide: NotificationService,
                    useValue: {
                        register: jest.fn().mockResolvedValue({}),
                    },
                },
            ],
        }).compile();

        app = moduleFixture.createNestApplication();
        service = moduleFixture.get<NotificationService>(NotificationService);
        await app.init();
    });

    it("/POST create", async () => {
        const dto: CreateRequestDTO = {
            event_id: "1",
            send_at: new Date(),
            status: NotificationStatus.Pending,
        };
        const response = null;

        jest.spyOn(service, "register").mockResolvedValue(response);

        return request(app.getHttpServer()).post("/reminder").send(dto).expect(201);
    });

    describe("NotificationController", () => {
        let app: INestApplication;
        let service: NotificationService;

        beforeAll(async () => {
            const moduleFixture: TestingModule = await Test.createTestingModule({
                controllers: [NotificationController],
                providers: [
                    {
                        provide: NotificationService,
                        useValue: {
                            register: jest.fn().mockResolvedValue({}),
                            get: jest.fn().mockResolvedValue({}),
                            getFilteredList: jest.fn().mockResolvedValue([]),
                            update: jest.fn().mockResolvedValue({}),
                            delete: jest.fn().mockResolvedValue({}),
                        },
                    },
                ],
            }).compile();

            app = moduleFixture.createNestApplication();
            service = moduleFixture.get<NotificationService>(NotificationService);
            await app.init();
        });

        it("/POST create", async () => {
            const dto: CreateRequestDTO = {
                event_id: "1",
                send_at: new Date(),
                status: NotificationStatus.Pending,
            };
            const response = undefined;

            jest.spyOn(service, "register").mockResolvedValue(response);

            return request(app.getHttpServer()).post("/reminder").send(dto).expect(201);
        });

        it("/GET read", async () => {
            const response = undefined;

            jest.spyOn(service, "get").mockResolvedValue(response);

            return request(app.getHttpServer()).get("/reminder/1").expect(200);
        });

        it("/GET readByOptions", async () => {
            const query: ParametersDTO = { status: NotificationStatus.Pending };
            const response = [];

            jest.spyOn(service, "getFilteredList").mockResolvedValue(response);

            return request(app.getHttpServer()).get("/reminder").query(query).expect(200);
        });

        it("/PUT update", async () => {
            const bodyDTO: CreateRequestDTO = {
                event_id: "1",
                send_at: new Date(),
                status: NotificationStatus.Pending,
            };
            const response = undefined;

            jest.spyOn(service, "register").mockResolvedValue(response);

            return request(app.getHttpServer()).put("/reminder/1").send(bodyDTO).expect(200);
        });

        it("/PATCH updatePartial", async () => {
            const bodyDTO: UpdateRequestDTO = {
                status: NotificationStatus.Sent,
                event_id: "",
            };
            const response = undefined;

            jest.spyOn(service, "update").mockResolvedValue(response);

            return request(app.getHttpServer()).patch("/reminder/1").send(bodyDTO).expect(200);
        });

        it("/DELETE delete", async () => {
            const response = undefined;

            jest.spyOn(service, "delete").mockResolvedValue(response);

            return request(app.getHttpServer()).delete("/reminder/1").expect(200);
        });

        afterAll(async () => {
            await app.close();
        });
    });

    afterAll(async () => {
        await app.close();
    });
});
