import { NestFactory } from "@nestjs/core";

import { WorkerModule } from "./module";

/**
 * 애플리케이션 부트스트랩 함수
 *
 * 이 함수는 NestJS 애플리케이션을 초기화하고 설정을 적용한 뒤
 * HTTP 서버와 마이크로서비스를 시작합니다.
 *
 * @returns {Promise<void>} 애플리케이션 초기화 및 실행 완료 후 반환
 */
export async function bootstrap(): Promise<void> {
    const app = await NestFactory.create(WorkerModule);

    await app.init();
}

if (require.main === module) {
    bootstrap();
}
