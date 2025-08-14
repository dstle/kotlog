package com.study.kotlog.front.controller.comment.dto

import com.study.kotlog.domain.comment.dto.UpdateCommentCommand

data class UpdateCommentRequest(
    val content: String,
) {
    fun toCommand(
        userId: Long,
        commentId: Long,
    ): UpdateCommentCommand = UpdateCommentCommand(
        authorId = userId,
        commentId = commentId,
        content = this.content
    )
}
