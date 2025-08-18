package com.study.kotlog.front.common.support

import com.study.kotlog.exception.FrontErrorCode
import com.study.kotlog.exception.FrontException
import com.study.kotlog.front.common.web.MemberRequest
import com.study.kotlog.util.JwtUtil
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
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

        try {
            jwtUtil.validateToken(jwt)
        } catch (e: ExpiredJwtException) {
            LOG.info("JWT expired token=$jwt", e)
            throw FrontException(FrontErrorCode.EXPIRED_JWT)
        } catch (e: Exception) {
            LOG.error("JWT validateToken error jwt=$jwt", e)
            throw FrontException(FrontErrorCode.INVALID_JWT)
        }

        val userId = jwtUtil.extractUserId(jwt)
        request.setAttribute("memberRequest", MemberRequest(userId))

        return true
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}
