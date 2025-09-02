package com.study.kotlog.domain.post

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class LikeStateStore(
    private val redis: StringRedisTemplate,
) {
    private fun setKey(postId: Long) = "post:$postId:liked_user"
    private val setOps = redis.opsForSet()

    fun like(
        postId: Long,
        userId: Long,
    ): Boolean = (setOps.add(setKey(postId), userId.toString()) ?: 0L) > 0L

    fun unlike(
        postId: Long,
        userId: Long,
    ): Boolean = (setOps.remove(setKey(postId), userId.toString()) ?: 0L) > 0L

    fun isLiked(
        postId: Long,
        userId: Long,
    ): Boolean = setOps.isMember(setKey(postId), userId.toString()) == true
}
