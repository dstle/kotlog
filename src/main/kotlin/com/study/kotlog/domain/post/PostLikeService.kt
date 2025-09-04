package com.study.kotlog.domain.post

import com.study.kotlog.domain.post.dto.LikeStatusResult
import org.springframework.stereotype.Service

@Service
class PostLikeService(
    private val likeStateStore: LikeStateStore,
    private val likeCountStore: LikeCountStore,
) {
    fun getLikeStatus(
        postId: Long,
        userId: Long,
    ): LikeStatusResult = LikeStatusResult(
        isLiked = likeStateStore.isLiked(postId, userId),
        likeCount = likeCountStore.getTotal(postId)
    )

    fun like(
        postId: Long,
        userId: Long,
    ) {
        if (likeStateStore.like(postId, userId)) {
            likeCountStore.increment(postId)
        }
    }

    fun unlike(
        postId: Long,
        userId: Long,
    ) {
        if (likeStateStore.unlike(postId, userId)) {
            likeCountStore.decrement(postId)
        }
    }
}
