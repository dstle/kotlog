package com.study.kotlog.domain.post.dto

data class UpdatePostCommand(
    val authorId: Long,
    val postId: Long,
    val title: String,
    val content: String,
)
