package com.study.kotlog.front.common.support

import com.study.kotlog.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(
    private val jwtUtil: JwtUtil,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val token = request.getHeader("Authorization")

        if (token.isNullOrEmpty() || !token.startsWith("Bearer ")) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        }

        val jwt = token.removePrefix("Bearer ").trim()

        if (!jwtUtil.validateToken(jwt)) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        }

        val userId = jwtUtil.extractUserId(jwt)
        request.setAttribute("userId", userId)

        return true
    }
}
