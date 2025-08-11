package com.study.kotlog.domain.comment.dto

data class CreateCommentCommand(
    val postId: Long,
    val authorId: Long,
    val content: String,
)
