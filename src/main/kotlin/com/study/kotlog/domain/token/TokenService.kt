package com.study.kotlog.domain.token

import com.study.kotlog.domain.token.dto.AccessToken
import com.study.kotlog.util.JwtUtil
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val jwtUtil: JwtUtil,
) {
    fun generateAccessToken(userId: Long): AccessToken = AccessToken(
        token = jwtUtil.generateToken(userId, ACCESS_TOKEN_EXPIRE_MILLIS),
        expiresIn = ACCESS_TOKEN_EXPIRE_SECONDS
    )

    fun generateRefreshToken(userId: Long): String = jwtUtil.generateToken(userId, REFRESH_TOKEN_EXPIRE_MILLIS)

    fun validateRefreshToken(refreshToken: String) {
        jwtUtil.validateToken(refreshToken)
    }

    fun extractUserId(refreshToken: String): Long = jwtUtil.extractUserId(refreshToken)

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_SECONDS = 60L // 30분
        private const val REFRESH_TOKEN_EXPIRE_SECONDS = 14 * 24 * 60 * 60L // 14일

        private const val ACCESS_TOKEN_EXPIRE_MILLIS = ACCESS_TOKEN_EXPIRE_SECONDS * 1000L
        private const val REFRESH_TOKEN_EXPIRE_MILLIS = REFRESH_TOKEN_EXPIRE_SECONDS * 1000L
    }
}
