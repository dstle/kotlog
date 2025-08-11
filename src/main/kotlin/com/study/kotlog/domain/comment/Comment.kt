package com.study.kotlog.domain.comment

import com.study.kotlog.domain.BaseEntity
import com.study.kotlog.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "comments")
class Comment(
    @Column(name = "post_id", nullable = false)
    val postId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "content", nullable = false)
    val content: String,
) : BaseEntity()
