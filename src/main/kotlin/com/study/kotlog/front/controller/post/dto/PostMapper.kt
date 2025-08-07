package com.study.kotlog.front.controller.post.dto

import com.study.kotlog.domain.post.Post

fun Post.toDto(): PostResponse = PostResponse(
    postId = this.id,
    authorId = this.authorId,
    title = this.title,
    content = this.content
)
