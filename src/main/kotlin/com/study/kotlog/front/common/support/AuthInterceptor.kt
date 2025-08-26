package com.study.kotlog.front.common.support

import com.study.kotlog.front.common.web.MemberRequest
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
            throw IllegalArgumentException("invalid header = $token")
        }

        val jwt = token.removePrefix("Bearer ").trim()

        jwtUtil.validateToken(jwt)

        val userId = jwtUtil.extractUserId(jwt)
        request.setAttribute("memberRequest", MemberRequest(userId))

        return true
    }
}
