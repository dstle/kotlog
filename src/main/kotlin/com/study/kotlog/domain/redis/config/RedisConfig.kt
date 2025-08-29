package com.study.kotlog.domain.redis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    val host: String,

    @Value("\${spring.data.redis.port}")
    val port: Int,

) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(host, port)

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> = RedisTemplate<String, String>().apply {
        this.connectionFactory = redisConnectionFactory()

        // "\xac\xed\x00" 같은 불필요한 해시값을 보지 않기 위해 serializer 설정
        this.keySerializer = StringRedisSerializer()
        this.hashKeySerializer = StringRedisSerializer()
        this.valueSerializer = StringRedisSerializer()
    }
}
