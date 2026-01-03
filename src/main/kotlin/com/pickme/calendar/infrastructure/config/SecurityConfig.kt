package com.pickme.calendar.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { } // CORS 필터를 활성화
            .csrf { it.disable() } // CSRF 비활성화, CSRF는 브라우저 + 쿠키 + 세션 조합에서 사용
            .authorizeHttpRequests { auth ->
                auth
                    // Swagger
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs**",
                        "/v3/api-docs/**",
                    ).permitAll()

                    // Actuator health
                    .requestMatchers(
                        "/actuator/health**",
                    ).permitAll()

                    // Internal API
                    .requestMatchers("/internal/**")
                    .hasAuthority("SCOPE_INTERNAL")

                    // External API
                    .requestMatchers("/api/**")
                    .authenticated()

                    // Default
                    .anyRequest().denyAll()
            }
            .oauth2ResourceServer {
                it.jwt {} // JWT 검증. JwtDecoder 자동 감지
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 거부
            }

        return http.build()
    }

    // 요청 URL 기준으로 어떤 CORS 정책을 적용할지 알려주는 객체
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedOriginPatterns = listOf("*") // 모든 Origin 허용
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("*") // 모든 요청 헤더 허용
        config.allowCredentials = false // 쿠키, 세션, 인증정보 포함 요청하지 않음

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config) // CORS 정책 적용
        return source
    }
}
