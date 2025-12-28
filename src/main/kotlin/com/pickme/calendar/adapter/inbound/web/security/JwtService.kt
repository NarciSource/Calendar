package com.pickme.calendar.adapter.inbound.web.security

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import org.springframework.stereotype.Service

@Service
class JwtService {
    // JWT토큰 처리하는 로직
    fun extractToken(token: String): String {
        return try {
            // JWT 토큰을 디코딩
            val decodedJwt = JWT.decode(token)
            // "client_id" 클레임을 문자열로 추출하여 반환
            decodedJwt.getClaim("client_id").asString()
                ?: throw CustomException(ErrorCode.INVALID_AUTH_TOKEN)
        } catch (_: JWTDecodeException) {
            throw CustomException(ErrorCode.INVALID_AUTH_TOKEN)
        }
    }
}