package com.study.kotlog.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.study.kotlog.domain.post.Post
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisStore(
    private val redisTemplate: StringRedisTemplate,
){
    fun set(
        key: String,
        value: Post,
    ){
        redisTemplate.opsForValue().set(key, OBJECT_MAPPER.writeValueAsString(value))
    }

    fun get(key: String): Post? {
        val value = redisTemplate.opsForValue()[key]
        return value?.let { OBJECT_MAPPER.readValue<Post>(it) }
    }

    companion object {
        private val OBJECT_MAPPER = ObjectMapper()
            .registerModule(JavaTimeModule())
            .registerKotlinModule()
    }
}
