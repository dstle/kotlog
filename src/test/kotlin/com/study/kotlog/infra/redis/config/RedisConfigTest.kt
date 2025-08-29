package com.study.kotlog.infra.redis.config

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate

@SpringBootTest
class RedisConfigTest(
    val redisTemplate: RedisTemplate<String, String>,
) : FunSpec({

    test("string 테스트") {
        val key = "debug:ping"
        val value = "dustle"
        val valueOperations = redisTemplate.opsForValue()

        valueOperations[key] = "dustle"

        valueOperations[key] shouldBe value
    }
})
