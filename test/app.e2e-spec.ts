import { Test, TestingModule } from "@nestjs/testing";
import { INestApplication } from "@nestjs/common";
import * as request from "supertest";

import { NotificationModule } from "../src/module";

describe("Bootstrap E2E Test", () => {
    let app: INestApplication;

    beforeAll(async () => {
        const moduleFixture: TestingModule = await Test.createTestingModule({
            imports: [NotificationModule], // NotificationModule을 테스트 모듈로 사용
        }).compile();

        app = moduleFixture.createNestApplication();
        await app.init();
        await app.listen(3000); // HTTP 서버를 3000번 포트에서 시작
    });

    afterAll(async () => {
        await app.close(); // 애플리케이션 종료
    });

    it("헬스 체크 엔드포인트에 응답", async () => {
        const response = await request(app.getHttpServer()).get("/");

        expect(response.status).toBe(200);
        expect(response.text).toEqual("OK");
    });

    it("알 수 없는 경로에 대해 404를 처리", async () => {
        const response = await request(app.getHttpServer()).get("/unknown-route");
        expect(response.status).toBe(404);
    });
});
