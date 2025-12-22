package com.pickme.calendar.adapter.inbound.web.security

import com.auth0.jwt.JWT
import org.springframework.stereotype.Service

@Service
class JwtService {
    // JWT토큰 처리하는 로직
    fun extractToken(bearerToken: String): String? {
        // "Bearer " 접두사 제거
        val token = bearerToken.replace("Bearer ", "")

        // JWT 토큰을 디코딩
        val decodedJwt = JWT.decode(token)

        // "client_id" 클레임을 문자열로 추출하여 반환
        return decodedJwt.getClaim("client_id").asString()
    }
}