package com.study.kotlog.domain.comment

import com.study.kotlog.domain.comment.dto.CreateCommentCommand
import com.study.kotlog.domain.post.Post
import com.study.kotlog.domain.post.PostRepository
import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

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
})
