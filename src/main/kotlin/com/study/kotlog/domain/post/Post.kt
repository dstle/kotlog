package com.study.kotlog.domain.post

import java.time.ZonedDateTime

data class Post (
    val id: Long,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val authorId: Long,
    val title: String,
    val content: String,
) {
    companion object {
        fun from(entity: PostEntity) = Post(
            id = entity.id,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            authorId = entity.authorId,
            title = entity.title,
            content = entity.content,
        )
    }
}
