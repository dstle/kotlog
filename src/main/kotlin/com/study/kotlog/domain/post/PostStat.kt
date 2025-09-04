package com.study.kotlog.domain.post

import com.study.kotlog.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "post_stats")
class PostStat(
    @Column(name = "post_id", nullable = false)
    val postId: Long,
    @Column(name = "likes", nullable = false)
    val likes: Long,
    @Column(name = "views", nullable = false)
    val views: Long,
) : BaseEntity()
