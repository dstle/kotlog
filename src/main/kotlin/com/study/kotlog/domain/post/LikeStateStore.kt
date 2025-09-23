package com.study.kotlog.domain.post

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class LikeStateStore(
    private val redisTemplate: StringRedisTemplate,
) {
    private fun setKey(postId: Long) = "post:$postId:liked_user"

    fun like(
        postId: Long,
        userId: Long,
    ): Boolean = (redisTemplate.opsForSet().add(setKey(postId), userId.toString()) ?: 0L) > 0L

    fun unlike(
        postId: Long,
        userId: Long,
    ): Boolean = (redisTemplate.opsForSet().remove(setKey(postId), userId.toString()) ?: 0L) > 0L

    fun isLiked(
        postId: Long,
        userId: Long,
    ): Boolean = redisTemplate.opsForSet().isMember(setKey(postId), userId.toString()) == true
}
