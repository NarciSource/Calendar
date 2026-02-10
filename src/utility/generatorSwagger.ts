import { INestApplication } from "@nestjs/common";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";

/**
 * 주어진 Nest 애플리케이션 인스턴스를 사용하여 Swagger 문서를 생성하고 설정합니다.
 *
 * @param app Swagger 문서를 생성할 대상이 되는 Nest 애플리케이션 인스턴스
 * @returns 생성된 Swagger 문서 객체
 *
 * @remarks
 * - Swagger 설정은 `DocumentBuilder`를 사용하여 구성됩니다.
 * - Bearer 인증 방식이 추가됩니다.
 * - 생성된 Swagger 문서는 `/api` 경로에서 UI로 접근할 수 있습니다.
 */
export default function generatorSwagger(app: INestApplication<any>) {
    // Swagger
    const swagger_config = new DocumentBuilder()
        .setTitle("알림 API")
        .setDescription("API 명세서")
        .setVersion("1.0")
        .addBearerAuth() // Bearer Auth 추가
        .build();

    // Swagger 설정
    const document = SwaggerModule.createDocument(app, swagger_config);
    // Swagger UI 설정
    SwaggerModule.setup("api", app, document);

    return document;
}
