package com.study.kotlog.front.controller.post.dto

import com.study.kotlog.domain.post.Post
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Post 단건 조회 DTO")
data class PostResponse(

    @Schema(description = "게시물 id", example = "1L")
    val postId: Long,

    @Schema(description = "작성자 id", example = "1")
    val authorId: Long,

    @Schema(description = "게시물 제목", example = "kotlog 만들기")
    val title: String,

    @Schema(description = "게시물 내용", example = "나는 너무 배부른 상태이다")
    val content: String,
) {
    companion object {
        fun from(post: Post): PostResponse = PostResponse(
            postId = post.id,
            authorId = post.authorId,
            title = post.title,
            content = post.content
        )
    }
}
