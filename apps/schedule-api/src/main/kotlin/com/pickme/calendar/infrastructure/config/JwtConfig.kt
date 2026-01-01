package com.pickme.calendar.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtConfig(
    @param:Value($$"${security.jwt.secret}")
    private val secret: String
) {

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secretKey = SecretKeySpec(
            secret.toByteArray(),
            "HmacSHA256"
        )

        return NimbusJwtDecoder
            .withSecretKey(secretKey)
            .macAlgorithm(MacAlgorithm.HS256)
            .build()
    }
}
