package com.study.kotlog.util

import com.study.kotlog.exception.FrontErrorCode
import com.study.kotlog.exception.FrontException
import io.jsonwebtoken.ExpiredJwtException
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
        expirationMs: Long,
    ): String {
        val now = Date()
        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(Date(now.time + expirationMs))
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            LOG.info("JWT expired token=$token", e)
            throw FrontException(FrontErrorCode.EXPIRED_TOKEN)
        } catch (e: Exception) {
            LOG.error("JWT validateToken error jwt=$token", e)
            throw FrontException(FrontErrorCode.INVALID_TOKEN)
        }
    }

    fun extractUserId(token: String): Long {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
        return claims.subject.toLong()
    }

    fun extractExpiration(token: String): Long {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.expiration.time
    }

    fun extractIssuedAt(token: String): Long {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.issuedAt.time
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}
