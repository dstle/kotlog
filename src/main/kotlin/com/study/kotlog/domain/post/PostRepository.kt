package com.study.kotlog.domain.post

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findByTitleContaining(
        keyword: String,
        pageable: Pageable,
    ): Page<Post>
}
