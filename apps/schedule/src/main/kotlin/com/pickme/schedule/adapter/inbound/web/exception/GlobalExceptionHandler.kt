package com.pickme.schedule.adapter.inbound.web.exception

import com.pickme.schedule.adapter.inbound.web.dto.response.ErrorResponseDto
import com.pickme.schedule.application.exception.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * 애플리케이션의 비즈니스 규칙 위반 또는 도메인 오류를 처리하기 위한 커스텀 예외
     *
     * 발생 시점:
     * - Service / UseCase / Domain 계층
     *
     * 설계 의도:
     * - ErrorCode를 통해 HTTP 상태 코드, 에러 코드, 메시지를 일관되게 관리
     * - 전역 ExceptionHandler에서 단일 방식으로 응답 변환
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<ErrorResponseDto<Nothing>> =
        ResponseEntity
            .status(exception.errorCode.httpStatus)
            .body(
                ErrorResponseDto(false, exception.message, null)
            )

    /**
     * 요청 본문(Request Body)을 객체로 역직렬화(deserialize)하지 못했을 때 발생하는 예외를 처리
     *
     * 발생 시점:
     * - HTTP Message Converter(Jackson 등)가 JSON → DTO 변환을 시도하는 단계
     *
     * 주요 원인:
     * - JSON 문법 오류 (잘못된 형식, 닫히지 않은 중괄호 등)
     * - 타입 불일치 (String → Int 등)
     * - Enum 값 불일치
     * - 날짜/시간 포맷 오류
     * - Kotlin non-null 필드 누락
     *
     * 특징:
     * - DTO 객체가 생성되지 않아 @Valid 검증 단계까지 도달하지 않음
     * - 필드 단위 오류 정보는 제한적
     *
     * 대응:
     * - 클라이언트 요청 자체가 잘못되었음을 알리는 400(Bad Request) 응답 반환
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadable(): ResponseEntity<ErrorResponseDto<Nothing>> =
        ResponseEntity
            .badRequest()
            .body(
                ErrorResponseDto(false, "요청 본문 형식이 올바르지 않습니다.", null)
            )

    /**
     * @Valid 또는 @Validated가 적용된 요청 DTO의 Validation 검증에 실패했을 때 발생하는 예외를 처리
     *
     * 발생 시점:
     * - JSON → DTO 변환이 정상적으로 완료된 이후
     * - Controller 메서드 호출 직전의 Validation 단계
     *
     * 주요 원인:
     * - @NotNull, @NotBlank, @Size, @Min 등의 제약 조건 위반
     * - 필드 값은 존재하지만 도메인 규칙에 맞지 않는 경우
     *
     * 특징:
     * - DTO 객체는 생성된 상태이므로 BindingResult 접근 가능
     * - 필드별 오류 정보(FieldError)를 상세히 수집할 수 있다.
     *
     * 대응 전략:
     * - 필드 단위 에러 메시지를 구조화하여 클라이언트에 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDto<*>> {
        val errors = exception.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "invalid")
        }

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponseDto(success = false, message = "요청 값 검증 실패", data = errors)
            )
    }

}