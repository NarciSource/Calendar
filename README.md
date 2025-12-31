# 캘린더 서비스

> API를 통해 사용자가 일정 캘린더를 관리할 수 있도록 지원하는 서비스

## 🚩 목차

- [🛠️ 기술 스택](#️-기술-스택)
- [🔐 인증/인가](#-인증인가)
- [📄 API 명세서](#-api-명세서)
- [📂 폴더 구조](#-폴더-구조)
- [🚀 실행 방법](#-실행-방법)
  - [로컬환경](#로컬환경)
  - [도커환경](#도커환경)

## 🛠️ 기술 스택

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)  
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white)  
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat&logo=MongoDB&logoColor=white)  
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=swagger&logoColor=black)

## 🔐 인증/인가

> 본 서비스는 외부 인증을 API Gateway에서 수행하며,  
Gateway &rarr; Backend 간 통신에는 내부 전용 JWT(Internal Token)를 사용한다.

- Format: JWT (JSON Web Token)
- Signing Algorithm: HS256 (Symmetric Key)
- Usage Scope: Gateway &rarr; Backend 내부 통신 전용
- Lifetime: Short-lived (60초)

### Token Claims Specification

```json
{
  "iss": "api-gateway",
  "sub": "user:123",
  "scp": ["USER", "INTERNAL"],
  "iat": 1735700000,
  "exp": 1735700060
}
```

| Claim | Type     |      Description |
| :---: | :------- | ---------------: |
| `iss` | String   |      토큰 발급자 |
| `sub` | String   | 인증 주체 식별자 |
| `scp` | String[] |        권한 범위 |
| `iat` | Number   |   토큰 발급 시각 |
| `exp` | Number   |   토큰 만료 시각 |

### Authorization Rules

| Endpoint       | Required Authority |
| -------------- | :----------------- |
| `/api/**`      | Authenticated      |
| `/internal/**` | `SCOPE_INTERNAL`   |


## 📄 API 명세서

[API 문서 바로가기](https://narcisource.github.io/Calendar/)

### API Summary

> URI 기준 인덱스 표

- Calendar
  | Method | URI                             | Summary        |  Auth  |  Path  |  Query   | Body  | Role  |
  | ------ | ------------------------------- | -------------- | :----: | :----: | :------: | :---: | :---: |
  | GET    | `/api/v2/schedule/{scheduleId}` | 일정 조회      | Bearer | String |    -     |   -   | USER  |
  | POST   | `/api/v2/schedule`              | 일정 추가      | Bearer |   -    |    -     | JSON  | USER  |
  | PUT    | `/api/v2/schedule/{scheduleId}` | 일정 수정      | Bearer | String |    -     | JSON  | USER  |
  | DELETE | `/api/v2/schedule/{scheduleId}` | 일정 삭제      | Bearer | String |    -     |   -   | USER  |
  | GET    | `/api/v2/schedules`             | 일정 조건 조회 | Bearer |   -    | Multiple |   -   | USER  |

- Internal
  | Method | URI                                | Summary        |  Auth  | Path  |     Query     | Body  |  Role   |
  | ------ | ---------------------------------- | -------------- | :----: | :---: | :-----------: | :---: | :-----: |
  | GET    | `/internal/schedules?scheduleIds=` | 일정 모음 조회 | Bearer |   -   | List\<String> |   -   | SERVICE |

### Path Variables

| Name       | Type   | Required | Describe | Example                  |
| ---------- | ------ | :------: | -------- | ------------------------ |
| scheduleId | String |    Y     | 일정ID   | 694d1d9462a47e4039250532 |

### Response Body

> 공통으로 사용하는 응답 래퍼

| Field   |  Type   | Required | Description      |
| ------- | :-----: | :------: | ---------------- |
| success | Boolean |    Y     | 요청 성공 여부   |
| message | String  |    Y     | 사용자 메시지    |
| data    |    T    |    N     | 실제 응답 데이터 |

### Status Code

> 글로벌 에러 정책

| Status | Success | Message                             | Data  |      Description |
| ------ | ------- | ----------------------------------- | :---: | ---------------: |
| 200    | true    | 면접 일정 조회 성공                 |   T   |        정상 응답 |
| 400    | false   | 요청 본문 형식이 올바르지 않습니다. | null  |   타입 변환 실패 |
| 400    | false   | 요청 값 검증 실패                   |   T   | 유효성 검증 실패 |
| 401    | false   | 인증 토큰에 문제가 있습니다.        | null  |        인증 실패 |
| 404    | false   | 해당하는 면접 일정이 없습니다.      | null  |      리소스 없음 |

### Swagger

- Swagger 페이지: `/swagger-ui`
- Api-docs 페이지: `/v3/api-docs`

## 📂 폴더 구조

<details>
<summary>열기</summary>

> Clean Architecture

```
Calendar
├─ .github
│  └─ workflows # GitHub-Actions
│     └─ generate-openapi.yml # openapi 배포 워크플로어
├─ .editorconfig # 코드 스타일 설정 파일
├─ .env # 환경변수
├─ Dockerfile # 도커파일
├─ settings.gradle.kts # 멀티 프로젝트 설정
├─ build.gradle.kts # 빌드 스크립트
└─ src/main
   ├─ resources
   │  ├─ application.yml
   │  ├─ application-dev.yml # 개발용 설정
   │  ├─ application-prod.yml # 배포용 설정
   │  └─ application-openapi.yml # 문서 생성용 설정
   └─ kotlin/com/pickme/calendar
      ├─ CalendarApplication.kt # 진입점
      ├─ domain/model
      │  ├─ Schedule.kt
      │  └─ InterviewSchedule.kt
      ├─ application
      │  ├─ annotation
      │  │  └─ UseCase.kt
      │  ├─ exception # 예외 enum
      │  │  ├─ ErrorCode.kt
      │  │  └─ CustomException.kt
      │  ├─ port/out
      │  │  └─ ScheduleRepository.kt # 레포지토리 인터페이스
      │  └─ usecase # 유스케이스
      │     ├─ FindSchedules.kt
      │     ├─ GetSchedule.kt
      │     ├─ GetSchedules.kt
      │     ├─ RegisterSchedule.kt
      │     ├─ UpdateSchedule.kt
      │     └─ DeleteSchedule.kt
      ├─ adapter
      │  ├─ inbound
      │  │  └─ web
      │  │     ├─ api
      │  │     │  └─ ApiVersion.kt # Api version 상수
      │  │     ├─ exception
      │  │     │  └─ GlobalExceptionHandler.kt # 공용 에외 핸들러
      │  │     ├─ dto
      │  │     │  ├─ request
      │  │     │  │  ├─ payload
      │  │     │  │  │  └─ CompanyDto.kt
      │  │     │  │  ├─ SearchQueryDto.kt
      │  │     │  │  ├─ PostScheduleDto.kt
      │  │     │  │  └─ PutScheduleDto.kt
      │  │     │  └─ response
      │  │     │     ├─ ResponseDto.kt
      │  │     │     ├─ ErrorResponseDto.kt
      │  │     │     ├─ ScheduleDto.kt
      │  │     │     └─ ScheduleIdDto.kt
      │  │     ├─ mapper # DTO-Entity 변환기
      │  │     │  └─ InterviewScheduleMapper.kt
      │  │     └─ controller # 컨트롤러
      │  │        ├─ CalendarController.kt
      │  │        └─ InternalController.kt
      │  └─ outbound
      │     └─ persistence # 레포지토리 구현체
      │        ├─ ScheduleCrudRepository.kt
      │        └─ MongoScheduleRepository.kt
      └─ infrastructure
          ├─ config
          │  ├─ WebConfig.kt # 웹 MVC 공통 설정
          │  ├─ JwtConfig.kt # JWT 토큰 검증
          │  ├─ SecurityConfig.kt # Spring Security 필터 체인, 인가/인증 정책 설정
          │  ├─ MongodbConfig.kt # MongoDB 연결
          │  ├─ OpenApiGroupConfig.kt # OpenAPI 그룹 분리 설정
          │  ├─ OpenApiStubConfig.kt # 개발용 OpenAPI 스텁
          │  └─ SwaggerConfig.kt # Swagger UI 메타데이터 설정
          └─ schema
             └─ MongoIndexInitializer # 몽고디비 인덱스 초기화
```

</details>

## 🚀 실행 방법

### 로컬환경

```sh
# 환경변수 export
$ export $(grep -v '^#' .env | xargs)

# MongoDB 서버의 27017 포트에서 실행을 가정
## MONGO_INITDB_ROOT_USERNAME=$MONGODB_USERNAME
## MONGO_INITDB_ROOT_PASSWORD=$MONGODB_PASSWORD
## MONGO_INITDB_DATABASE=$MONGODB_DATABASE

# 스프링 부트 실행
$ ./gradlew bootRun
```

### 도커환경

```sh
$ export $(grep -v '^#' .env | xargs)

# MongoDB (docker)
$ docker run -d \
  --name mongo-container \
  -e MONGO_INITDB_ROOT_USERNAME=$MONGODB_USERNAME \
  -e MONGO_INITDB_ROOT_PASSWORD=$MONGODB_PASSWORD \
  -e MONGO_INITDB_DATABASE=$MONGODB_DATABASE \
  -p 27017:27017 \
  mongo:latest

# 도커 컨테이너 빌드
$ docker build -t calendar/server .

# 도커 컨테이너 실행
$ docker run -d \
 --name calendar/server
 --env-file .env \
 -p 8080:8080 \
 calendar/server:latest
```
