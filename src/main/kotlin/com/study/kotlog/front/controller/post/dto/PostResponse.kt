package com.study.kotlog.front.controller.post.dto

import com.study.kotlog.domain.post.Post
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Post 생성 후 반환 DTO")
data class PostResponse(

    @Schema(description = "게시물 id", example = "1L")
    val postId: Long,
) {
    companion object {
        fun from(post: Post): PostResponse = PostResponse(
            postId = post.id
        )
    }
}
