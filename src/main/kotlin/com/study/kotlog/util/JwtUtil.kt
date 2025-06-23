package com.study.kotlog.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {
    private val secret: String = "mysecretmysecretmysecretmysecretmysecretmysecret"
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(
        userId: Long,
        expirationMs: Long = ONE_HOURS_MS,
    ): String {
        val now = Date()
        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(Date(now.time + expirationMs))
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean = try {
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
        true
    } catch (e: Exception) {
        LOG.error("Invalid JWT token", e)
        false
    }

    fun extractUserId(token: String): Long {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
        return claims.subject.toLong()
    }

    companion object {
        private const val ONE_HOURS_MS = 60 * 60 * 1000L
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}
