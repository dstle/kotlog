package com.study.kotlog.front.controller.post.dto

import com.study.kotlog.domain.post.dto.UpdatePostCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Post 수정 Request DTO")
data class UpdatePostRequest(

    @Schema(description = "게시물 제목", example = "kotlog 제목 수정")
    val title: String,

    @Schema(description = "게시물 내용", example = "수정을 합니다")
    val content: String,
) {
    fun toCommand(
        authorId: Long,
        postId: Long,
    ): UpdatePostCommand = UpdatePostCommand(
        authorId = authorId,
        postId = postId,
        title = title,
        content = content
    )
}
