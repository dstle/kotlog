package com.study.kotlog.domain.comment

import com.study.kotlog.domain.comment.dto.CreateCommentCommand
import com.study.kotlog.domain.post.Post
import com.study.kotlog.domain.post.PostRepository
import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class CommentServiceTest(
    val commentService: CommentService,
    val postRepository: PostRepository,
    val userRepository: UserRepository,
    val commentRepository: CommentRepository,
) : FunSpec({
    beforeTest {
        postRepository.deleteAll()
    }

    context("comment 생성") {
        test("comment 생성 성공") {
            val user = User(
                username = "dustle",
                password = "111",
                email = "111",
                nickname = "111"
            ).also {
                userRepository.save(it)
            }

            val post = Post(
                authorId = user.id,
                title = "title",
                content = "content"
            ).also {
                postRepository.save(it)
            }

            val comment = CreateCommentCommand(
                authorId = user.id,
                postId = post.id,
                content = "test"
            )

            val createComment = commentService.createComment(comment)

            createComment.user.id shouldBe comment.authorId
            createComment.postId shouldBe comment.postId
            createComment.content shouldBe comment.content
        }
    }

    context("댓글 조회") {
        test("postId 로 댓글 조회 성공") {
            val user = User(
                username = "dustle",
                password = "111",
                email = "111",
                nickname = "111"
            ).also {
                userRepository.save(it)
            }

            val post = Post(
                authorId = 1L,
                title = "title",
                content = "content"
            ).also {
                postRepository.save(it)
            }

            (1..5).forEach { i ->
                Comment(
                    user = user,
                    postId = post.id,
                    content = "Content $i"
                ).also {
                    commentRepository.save(it)
                }
            }
            commentService.getComments(post.id).size shouldBe 5
        }
    }

    context("댓글 삭제") {
        test("commentId 로 댓글 삭제") {
            val user = User(
                username = "dustle",
                password = "111",
                email = "111",
                nickname = "111"
            ).also {
                userRepository.save(it)
            }

            val post = Post(
                authorId = user.id,
                title = "title",
                content = "content"
            ).also {
                postRepository.save(it)
            }

            val comment = Comment(
                user = user,
                postId = post.id,
                content = "test"
            ).also {
                commentRepository.save(it)
            }

            commentService.deleteComment(comment.id, user.id)

            commentRepository.findById(comment.id) shouldBe Optional.empty()
        }

        test("commentId 작성자가 달라서 삭제 실패") {
            val user1 = User(
                username = "dustle11",
                password = "111",
                email = "111",
                nickname = "111"
            ).also {
                userRepository.save(it)
            }

            val user2 = User(
                username = "dustle1",
                password = "111",
                email = "111",
                nickname = "111"
            ).also {
                userRepository.save(it)
            }

            val post = Post(
                authorId = user1.id,
                title = "title",
                content = "content"
            ).also {
                postRepository.save(it)
            }

            val comment = Comment(
                user = user1,
                postId = post.id,
                content = "test"
            ).also {
                commentRepository.save(it)
            }

            shouldThrow<IllegalArgumentException> {
                commentService.deleteComment(comment.id, user2.id)
            }.message shouldBe "User with id ${user2.id} does not belong to this comment"
        }

        test("commentId 의 comment 가 없어서 삭제 실패") {
            val user = User(
                username = "dustle",
                password = "111",
                email = "111",
                nickname = "111"
            ).also {
                userRepository.save(it)
            }

            val commentId: Long = 100

            shouldThrow<IllegalArgumentException> {
                commentService.deleteComment(commentId, user.id)
            }.message shouldBe "Comment with commentId $commentId does not exist"
        }
    }
})
