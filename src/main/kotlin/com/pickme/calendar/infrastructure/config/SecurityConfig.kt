package com.pickme.calendar.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
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

                    // Health Check
                    .requestMatchers(
                        "/",
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

                it.authenticationEntryPoint { _, response, _ ->
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.writer.write("INVALID_AUTH_TOKEN")
                }
            }
            .exceptionHandling {
                it.accessDeniedHandler { _, response, _ ->
                    response.status = HttpStatus.FORBIDDEN.value()
                    response.writer.write("FORBIDDEN")
                }
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 거부
            }

        return http.build()
    }
}
