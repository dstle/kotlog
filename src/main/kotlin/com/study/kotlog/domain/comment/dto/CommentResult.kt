package com.study.kotlog.domain.comment.dto

import com.study.kotlog.domain.comment.Comment

data class CommentResult(
    val nickname: String,
    val content: String,
) {
    companion object {
        fun from(comment: Comment): CommentResult = CommentResult(
            nickname = comment.user.nickname,
            content = comment.content
        )
    }
}
