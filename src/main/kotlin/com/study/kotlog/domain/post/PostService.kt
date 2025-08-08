package com.study.kotlog.domain.post

import com.study.kotlog.domain.post.dto.CreatePostCommand
import com.study.kotlog.domain.post.dto.UpdatePostCommand
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    fun getPost(postId: Long): Post = postRepository.findById(postId)
        .orElseThrow { IllegalArgumentException("Post with id $postId does not exist") }

    fun getPosts(
        keyword: String?,
        pageable: Pageable,
    ): Page<Post> = if (keyword.isNullOrBlank()) {
        postRepository.findAll(pageable)
    } else {
        postRepository.findByTitleContaining(keyword, pageable)
    }

    @Transactional
    fun updatePost(updatePostCommand: UpdatePostCommand): Post {
        val post = getPost(updatePostCommand.postId)

        if (post.authorId != updatePostCommand.authorId) {
            throw IllegalArgumentException("Post with postId : ${updatePostCommand.postId} does not belong to author id : ${updatePostCommand.authorId}")
        }

        post.title = updatePostCommand.title
        post.content = updatePostCommand.content

        return post
    }
}
