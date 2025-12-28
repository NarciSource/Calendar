package com.pickme.calendar.adapter.inbound.web.interceptor

import com.pickme.calendar.adapter.inbound.web.security.JwtService
import com.pickme.calendar.application.exception.CustomException
import com.pickme.calendar.application.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springdoc.core.utils.Constants.OPTIONS_METHOD
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor(val jwtService: JwtService) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean = when (request.method.uppercase()) {
        OPTIONS_METHOD -> true

        else -> {
            // 클라이언트 요청 헤더에서 Authorization 정보를 가져옴
            request.getHeader("Authorization")
                ?.takeIf { it.startsWith("Bearer ") }
                ?.removePrefix("Bearer ")
                ?.trim()
                ?.let(jwtService::extractToken)
                ?.also { request.setAttribute("clientId", it) }
                ?: throw CustomException(ErrorCode.INVALID_AUTH_TOKEN)
            true
        }
    }
}