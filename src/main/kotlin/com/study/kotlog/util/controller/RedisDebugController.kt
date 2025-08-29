package com.study.kotlog.util.controller

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/api/v1/redis")
class RedisDebugController(
    private val redis: StringRedisTemplate,
) {

    @GetMapping("/ping")
    fun ping(): String {
        val key = "debug:ping"
        redis.opsForValue().set(key, "pong", Duration.ofSeconds(10))

        val v = redis.opsForValue().get(key) ?: "null"
        return "redis ok: $v"
    }
}
