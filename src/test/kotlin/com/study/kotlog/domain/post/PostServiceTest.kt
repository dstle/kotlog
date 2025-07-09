package com.study.kotlog.domain.post

import com.study.kotlog.domain.post.dto.CreatePostCommand
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostServiceTest(
    val postService: PostService,
    val postRepository: PostRepository,
) : FunSpec({
    test("게시물 하나 등록 성공") {
        val command = CreatePostCommand(
            authorId = 1L,
            title = "게시물 하나를 테스트 하는 법",
            content = "너무 덥고 아이스 아메리카노가 먹고싶습니다."
        )

        val response = postService.createPost(command)

        val savedPost = postRepository.findById(response.id).get()

        savedPost.title shouldBe command.title
        savedPost.content shouldBe command.content
        savedPost.authorId shouldBe command.authorId
    }
})
