package com.study.kotlog.domain.post

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class LikeCountStore(
    private val redis: StringRedisTemplate,
) {
    private val totalKey = "post:like:total"
    private val deltaKey = "post:like:delta"
    private val hashOps = redis.opsForHash<String, String>()

    fun increment(postId: Long): Long {
        hashOps.increment(deltaKey, postId.toString(), 1L)
        return hashOps.increment(totalKey, postId.toString(), 1L)
    }

    fun decrement(postId: Long): Long {
        hashOps.increment(deltaKey, postId.toString(), -1L)
        return hashOps.increment(totalKey, postId.toString(), -1L)
    }

    fun getTotal(postId: Long): Long = hashOps.get(totalKey, postId.toString())?.toLong() ?: 0
}
