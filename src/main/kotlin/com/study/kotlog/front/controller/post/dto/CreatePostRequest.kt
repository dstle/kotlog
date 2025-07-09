package com.study.kotlog.front.controller.post.dto

import com.study.kotlog.domain.post.dto.CreatePostCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "게시글 생성 DTO")
data class CreatePostRequest(
    @Schema(description = "게시글 제목", example = "kotlog 만들기")
    val title: String,
    @Schema(description = "게시글 내용", example = "kotlin 으로 블로그를 만들어보겠습니다.")
    val content: String,
) {
    fun toCommand(authorId: Long): CreatePostCommand = CreatePostCommand(
        authorId = authorId,
        title = this.title,
        content = this.content
    )
}
