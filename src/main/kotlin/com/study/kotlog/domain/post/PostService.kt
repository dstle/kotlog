package com.study.kotlog.domain.post

import com.study.kotlog.domain.post.dto.CreatePostCommand
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
) {

    fun createPost(createPostCommand: CreatePostCommand): Post {
        val post = Post.of(
            authorId = createPostCommand.authorId,
            title = createPostCommand.title,
            content = createPostCommand.content
        )

        postRepository.save(post)

        return post
    }
}
