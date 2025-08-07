package com.study.kotlog.domain.post

import com.study.kotlog.domain.post.dto.CreatePostCommand
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable

@SpringBootTest
class PostServiceTest(
    val postService: PostService,
    val postRepository: PostRepository,
) : FunSpec({
    beforeTest {
        postRepository.deleteAll()
    }

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

    test("게시물 단건 조회 성공") {
        val command = CreatePostCommand(
            authorId = 2L,
            title = "게시물 하나를 조회하기 위한 생성",
            content = "생성한다."
        )

        val createdPost = postService.createPost(command)

        val post = postService.getPost(createdPost.id)

        post.id shouldBe createdPost.id
        post.title shouldBe createdPost.title
        post.content shouldBe createdPost.content
        post.authorId shouldBe createdPost.authorId
    }

    test("게시물 다건 조회 검색 성공(키워드 X)") {
        (1..15).forEach { i ->
            val command = CreatePostCommand(
                authorId = i.toLong(),
                title = "Title $i",
                content = "Content $i"
            )

            postService.createPost(command)
        }

        val posts = postService.getPosts(null, Pageable.ofSize(10))

        posts.size shouldBe 10
    }

    test("게시물 다건 조회 검색 성공(키워드 O)") {
        (1..15).forEach { i ->
            val command = CreatePostCommand(
                authorId = i.toLong(),
                title = "Title $i",
                content = "Content $i"
            )

            postService.createPost(command)
        }

        val posts = postService.getPosts("Title", Pageable.ofSize(10))

        posts.content.forEach { post ->
            post.title shouldContain "Title"
        }

        posts.content.size shouldBe 10
    }
})
