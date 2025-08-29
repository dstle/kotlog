package com.study.kotlog.domain.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

@Component
class RefreshTokenStore(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    private fun key(userId: Long) = "rt:$userId"

    fun getSHA512(input: String): String {
        val md: MessageDigest = MessageDigest.getInstance("SHA-512")
        val messageDigest = md.digest(input.toByteArray())

        return messageDigest.joinToString("") { "%02x".format(it) }
    }

    fun save(
        userId: Long,
        refreshToken: String,
        ttl: Long,
    ) {
        val hash = getSHA512(refreshToken)

        redisTemplate.opsForValue().set(key(userId), hash, ttl, TimeUnit.SECONDS)
    }

    fun delete(userId: Long) {
        redisTemplate.delete(key(userId))
    }

    fun matchRefreshToken(
        refreshToken: String,
        redisRefreshToken: String,
    ): Boolean = redisRefreshToken == getSHA512(refreshToken)

    fun getHash(userId: Long): String? = redisTemplate.opsForValue().get(key(userId))
}
