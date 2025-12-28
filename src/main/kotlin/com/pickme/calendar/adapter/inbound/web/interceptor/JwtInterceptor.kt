package com.pickme.calendar.adapter.inbound.web.interceptor

import com.pickme.calendar.adapter.inbound.web.security.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor(val jwtService: JwtService) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {

        // OPTIONS 통과
        if ("OPTIONS".equals(request.method, ignoreCase = true)) {
            return true
        }

        // 클라이언트 요청 헤더에서 Authorization 정보를 가져옴
        val token = request.getHeader("Authorization")
        // Authorization 헤더가 없거나 Bearer 형식이 아니면 401(Unauthorized) 상태 코드를 반환하고 요청을 중단
        if (token == null || !token.startsWith("Bearer ")) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED // 응답 상태를 401로 설정
            return false // 요청 처리 중단
        }

        // 토큰에서 사용자 정보를 추출
        val clientId = jwtService.extractToken(token)

        // HttpServletRequest에 사용자 정보를 속성으로 추가하여 컨트롤러에서 사용할 수 있게 함
        request.setAttribute("clientId", clientId)

        // 요청 처리를 계속 진행
        return true
    }
}