package com.pickme.calendar.infrastructure.config

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiGroupConfig {

    @Bean
    fun externalApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("external")
            .pathsToMatch("/api/**")
            .build()

    @Bean
    fun internalApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("internal")
            .addOpenApiCustomizer {
                it.info.description = "내부 시스템 전용 API"
            }
            .pathsToMatch("/internal/**")
            .build()
}