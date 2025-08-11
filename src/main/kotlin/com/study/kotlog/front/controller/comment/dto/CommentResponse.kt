package com.study.kotlog.front.controller.comment.dto

import com.study.kotlog.domain.comment.dto.CommentResult
import io.swagger.v3.oas.annotations.media.Schema

data class CommentResponse(
    @Schema(description = "작성자 닉네임", example = "dustle")
    val nickname: String,
    @Schema(description = "댓글 내용", example = "잘봤습니다")
    val content: String,
) {
    companion object {
        fun from(commentResult: CommentResult): CommentResponse = CommentResponse(
            nickname = commentResult.nickname,
            content = commentResult.content
        )
    }
}
