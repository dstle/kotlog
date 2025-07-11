package com.study.kotlog.front.controller.post

import com.study.kotlog.domain.post.PostService
import com.study.kotlog.front.controller.post.dto.CreatePostRequest
import com.study.kotlog.front.controller.post.dto.PostResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService,
) {
    @PostMapping
    @Operation(summary = "post 생성", description = "현재 userId 로 게시물을 생성합니다.")
    fun createPost(
        userId: Long,
        @RequestBody request: CreatePostRequest,
    ): ResponseEntity<PostResponse> {
        val post = postService.createPost(request.toCommand(userId))

        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.from(post))
    }
}
