package com.study.kotlog.front.controller.comment.dto

import com.study.kotlog.domain.comment.dto.CreateCommentCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "댓글 생성 DTO")
data class CreateCommentRequest(

    @Schema(description = "게시글 아이디", example = "1")
    val postId: Long,

    @Schema(description = "댓글 내용", example = "너무 좋은 글입니다.")
    val content: String,
) {
    fun toCommand(userId: Long): CreateCommentCommand = CreateCommentCommand(
        authorId = userId,
        postId = this.postId,
        content = this.content
    )
}
