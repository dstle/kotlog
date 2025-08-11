package com.study.kotlog.domain.comment

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = ["user"])
    fun findByPostId(postId: Long): List<Comment>
}
