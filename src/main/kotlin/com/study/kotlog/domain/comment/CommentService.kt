package com.study.kotlog.domain.comment

import com.study.kotlog.domain.comment.dto.CommentResult
import com.study.kotlog.domain.comment.dto.CreateCommentCommand
import com.study.kotlog.domain.comment.dto.UpdateCommentCommand
import com.study.kotlog.domain.post.PostRepository
import com.study.kotlog.domain.user.UserRepository
import jakarta.transaction.Transactional
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
        val comment = getComment(commentId)

        validateCommentOwner(comment.user.id, memberId)

        commentRepository.delete(comment)
    }

    private fun validateCommentOwner(authorId: Long, memberId: Long) {
        if (authorId != memberId) {
            throw IllegalArgumentException("User with id $memberId does not belong to this comment")
        }
    }

    private fun getComment(commentId: Long): Comment {
        return commentRepository.findById(commentId)
            .orElseThrow { IllegalArgumentException("Comment with commentId $commentId does not exist") }
    }

    @Transactional
    fun updateComment(
        updateCommentCommand: UpdateCommentCommand,
    ): Comment {
        val comment = getComment(updateCommentCommand.commentId)

        validateCommentOwner(comment.user.id, updateCommentCommand.authorId)

        comment.updateContent(updateCommentCommand.content)

        return comment
    }
}
