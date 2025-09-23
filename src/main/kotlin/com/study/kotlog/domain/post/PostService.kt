package com.study.kotlog.domain.post

import com.study.kotlog.domain.post.dto.CreatePostCommand
import com.study.kotlog.domain.post.dto.UpdatePostCommand
import com.study.kotlog.infra.redis.RedisStore
import org.apache.commons.logging.Log
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val redisStore: RedisStore,
    private val postRepository: PostRepository,
) {
    fun createPost(createPostCommand: CreatePostCommand): Post {
        val postEntity = PostEntity.of(
            authorId = createPostCommand.authorId,
            title = createPostCommand.title,
            content = createPostCommand.content
        )

        postRepository.save(postEntity)

        return Post.from(postEntity)
    }

    fun getPost(postId: Long): Post {

        val cachedPost = redisStore.get(postId.toString())

        if(cachedPost != null) {
            return cachedPost
        }

        return postRepository.findByIdOrNull(postId)
            ?.let { Post.from(it) }
            ?.also { redisStore.set(postId.toString(), it) }
            ?: throw IllegalArgumentException("Post with postId $postId does not exist")
    }

    fun getPosts(
        keyword: String?,
        pageable: Pageable,
    ): Page<Post> {

        val postEntities = if (keyword.isNullOrBlank()) {
            postRepository.findAll(pageable)
        } else {
            postRepository.findByTitleContaining(keyword, pageable)
        }

        return postEntities.map { Post.from(it) }
    }

    @Transactional
    fun updatePost(updatePostCommand: UpdatePostCommand): Post {
        val postEntity = postRepository.findByIdOrNull(updatePostCommand.postId) ?: throw IllegalArgumentException("Post with id ${updatePostCommand.postId} does not exist")

        if (postEntity.authorId != updatePostCommand.authorId) {
            throw IllegalArgumentException("Post with postId : ${updatePostCommand.postId} does not belong to authorId : ${updatePostCommand.authorId}")
        }

        postEntity.title = updatePostCommand.title
        postEntity.content = updatePostCommand.content

        return Post.from(postEntity)
    }

    fun deletePost(
        userId: Long,
        postId: Long,
    ) {
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException("Post with id $postId does not exist")

        if (post.authorId != userId) {
            throw IllegalArgumentException("Post with postId : $postId does not belong to userId : $userId")
        }

        postRepository.delete(post)
    }

    companion object{
        private val LOG = LoggerFactory.getLogger(PostService::class.java)
    }
}
