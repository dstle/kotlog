package com.study.kotlog.domain.post.dto

data class CreatePostCommand(
    val authorId: Long,
    val title: String,
    val content: String,
)
