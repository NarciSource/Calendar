package com.pickme.calendar.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
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
}
