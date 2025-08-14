package com.study.kotlog.domain.comment.dto

data class UpdateCommentCommand (
    val authorId: Long,
    val commentId: Long,
    val content: String,
)
