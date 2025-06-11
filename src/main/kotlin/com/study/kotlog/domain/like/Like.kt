package com.study.kotlog.domain.like

import com.study.kotlog.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "likes")
class Like(

    @Column(name = "post_id", nullable = false)
    val postId: Long,

    @Column(name = "user_id", nullable = false)
    val userId: Long
) : BaseEntity()
