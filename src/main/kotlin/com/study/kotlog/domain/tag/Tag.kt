package com.study.kotlog.domain.tag

import com.study.kotlog.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tags")
class Tag(
    @Column(name = "name", nullable = false)
    val name: String,
) : BaseEntity()
