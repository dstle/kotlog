package com.study.kotlog.domain.comment

import com.study.kotlog.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "comments")
class Comment(
    @Column(name = "post_id", nullable = false)
    val postId: Long,
    @Column(name = "author_id", nullable = false)
    val authorId: Long,
    @Column(name = "content", nullable = false)
    val content: String,
) : BaseEntity()
