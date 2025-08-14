package com.study.kotlog.domain.comment

import com.study.kotlog.domain.comment.dto.CommentResult
import com.study.kotlog.domain.comment.dto.CreateCommentCommand
import com.study.kotlog.domain.post.PostRepository
import com.study.kotlog.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) {
    fun createComment(command: CreateCommentCommand): Comment {
        validatePostExists(command.postId)

        val referenceUser = userRepository.getReferenceById(command.authorId)
        val comment = Comment(
            user = referenceUser,
            postId = command.postId,
            content = command.content
        )

        commentRepository.save(comment)

        return comment
    }

    fun getComments(postId: Long): List<CommentResult> {
        validatePostExists(postId)

        return commentRepository.findByPostId(postId).map { CommentResult.from(it) }
    }

    private fun validatePostExists(postId: Long) {
        if (!postRepository.existsById(postId)) {
            throw IllegalArgumentException("post not found with id $postId")
        }
    }

    fun deleteComment(
        commentId: Long,
        memberId: Long,
    ) {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { IllegalArgumentException("Comment with commentId $commentId does not exist") }

        if (comment.user.id != memberId) {
            throw IllegalArgumentException("User with id $memberId does not belong to this comment")
        }

        commentRepository.delete(comment)
    }
}
