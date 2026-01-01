package com.pickme.calendar.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/*
 * WebMvcConfigurer:
 * - Spring MVC 설정을 커스터마이징할 수 있는 인터페이스.
 * - 인터셉터 등록, CORS 설정, 뷰 컨트롤러 추가 등 다양한 웹 관련 설정을 커스터마이징할 때 사용.
 */
@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
    }
}
