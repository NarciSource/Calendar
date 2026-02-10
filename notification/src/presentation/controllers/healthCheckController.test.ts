import { Test, TestingModule } from "@nestjs/testing";
import { INestApplication } from "@nestjs/common";
import * as request from "supertest";

import HealthCheckController from "./healthCheckController";

describe("HealthCheckController (e2e)", () => {
    let app: INestApplication;

    beforeAll(async () => {
        const moduleFixture: TestingModule = await Test.createTestingModule({
            controllers: [HealthCheckController],
        }).compile();

        app = moduleFixture.createNestApplication();
        await app.init();
    });

    afterAll(async () => {
        await app.close();
    });

    it("/GET read", async () => {
        const response = await request(app.getHttpServer()).get("/");

        expect(response.status).toBe(200);
        expect(response.text).toBe("OK");
    });
});
