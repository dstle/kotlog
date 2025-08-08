package com.study.kotlog.domain.post

import com.study.kotlog.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "posts")
class Post(
    @Column(name = "author_id", nullable = false)
    val authorId: Long,
    @Column(name = "title", nullable = false)
    val title: String,
    @Column(name = "content", nullable = false)
    val content: String,
) : BaseEntity() {
    companion object {
        fun of(
            authorId: Long,
            title: String,
            content: String,
        ): Post = Post(authorId, title, content)
    }

    override fun toString(): String = "Post(authorId=$authorId, title='$title', content='$content')"
}
