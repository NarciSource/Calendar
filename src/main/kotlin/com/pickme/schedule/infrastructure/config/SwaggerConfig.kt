package com.pickme.schedule.infrastructure.config

import com.pickme.schedule.adapter.inbound.web.api.ApiVersions
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration // 이 클래스가 스프링의 설정 파일임을 명시하는 어노테이션
class SwaggerConfig(
    @param:Value($$"${springdoc.swagger-ui.oauth.browser-access-url}")
    private val browserAccessUrl: String
) {

    // Swagger API 설정을 위한 Bean을 생성하는 메서드
    @Bean
    fun customOpenApi(): OpenAPI {

        val securityScheme = SecurityScheme() // API 인증 방식 정의
            .type(SecurityScheme.Type.OAUTH2) // 보안 방식으로 OAuth2 사용
            .description("Keycloak OAuth2 Authorization Code")
            .flows(
                OAuthFlows().authorizationCode(
                    OAuthFlow()
                        .authorizationUrl(
                            // Keycloak의 인증 엔드포인트 URL
                            "$browserAccessUrl/protocol/openid-connect/auth"
                        )
                        .tokenUrl(
                            // Keycloak의 토큰 발급 엔드포인트 URL
                            "$browserAccessUrl/protocol/openid-connect/token"
                        )
                        .scopes(
                            Scopes().addString("openid", "OpenID Connect")
                        )
                )
            )

        val oauthSchemeName = "OAuth2" // 보안 스킴 이름 정의

        // 요청 시 필요한 인증 요구사항 정의
        val securityRequirement = SecurityRequirement()
            .addList(oauthSchemeName, listOf("openid"))

        // OpenAPI 설정을 반환
        return OpenAPI()
            .info(
                Info()
                    .title("PickMe-Schedule")
                    .version(ApiVersions.V2)
            )
            .components(
                Components()
                    .addSecuritySchemes(oauthSchemeName, securityScheme)
            )
            .addSecurityItem(securityRequirement) // 모든 API 엔드포인트에 보안 요구사항 적용
    }
}
