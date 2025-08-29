package com.study.kotlog.domain.token

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate

@SpringBootTest
class RefreshTokenStoreTest(
    val refreshTokenStore: RefreshTokenStore,
    val redisTemplate: RedisTemplate<String, String>,
) : FunSpec(
    {
        test("레디스에 생성 성공") {
            val userId = 1L
            val refreshToken = "asdfasdfasdf"
            val ttlSeconds = 10L

            refreshTokenStore.save(userId, refreshToken, ttlSeconds)

            redisTemplate.opsForValue().get("rt:$userId").shouldNotBeNull()
        }

        test("레디스에서 삭제 성공") {
            val userId = 2L
            val refreshToken = "asdf123123"
            val ttlSeconds = 10000L

            refreshTokenStore.save(userId, refreshToken, ttlSeconds)

            redisTemplate.opsForValue().get("rt:$userId").shouldNotBeNull()

            refreshTokenStore.delete(userId)

            redisTemplate.opsForValue().get("rt:$userId").shouldBe(null)
        }

        test("레디스에서 RT 가져오기 성공") {
            val userId = 3L
            val refreshToken = "asdfasdfasdf"
            val ttlSeconds = 10000L

            refreshTokenStore.save(userId, refreshToken, ttlSeconds)

            refreshTokenStore.getHash(userId).shouldBe(refreshTokenStore.getSHA512(refreshToken))
        }
    }
)
