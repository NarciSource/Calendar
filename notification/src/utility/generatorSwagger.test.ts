import { Test } from "@nestjs/testing";
import { INestApplication } from "@nestjs/common";
import { SwaggerModule } from "@nestjs/swagger";

import generatorSwagger from "./generatorSwagger";

describe("generatorSwagger", () => {
    let app: INestApplication;
    let createDocumentSpy: jest.SpyInstance;
    let setupSpy: jest.SpyInstance;

    beforeAll(async () => {
        const moduleRef = await Test.createTestingModule({}).compile();
        app = moduleRef.createNestApplication();
        await app.init();

        createDocumentSpy = jest.spyOn(SwaggerModule, "createDocument");
        setupSpy = jest.spyOn(SwaggerModule, "setup");
    });

    afterAll(async () => {
        await app.close();
    });

    afterAll(() => {
        jest.clearAllMocks();

        createDocumentSpy.mockRestore();
        setupSpy.mockRestore();
    });

    it("Swagger 문서를 올바른 설정으로 생성", () => {
        const document = generatorSwagger(app);

        expect(createDocumentSpy).toHaveBeenCalledWith(
            app,
            expect.objectContaining({
                info: expect.objectContaining({
                    title: expect.any(String),
                    description: expect.any(String),
                    version: expect.any(String),
                }),
            }),
        );
        expect(setupSpy).toHaveBeenCalledWith("api", app, document);
        expect(document).toBeDefined();
    });
});
