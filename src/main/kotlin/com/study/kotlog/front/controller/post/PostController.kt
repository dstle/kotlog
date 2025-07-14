package com.study.kotlog.front.controller.post

import com.study.kotlog.domain.post.PostService
import com.study.kotlog.front.common.web.MemberRequest
import com.study.kotlog.front.controller.post.dto.CreatePostRequest
import com.study.kotlog.front.controller.post.dto.PostResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "/posts", description = "블로그 글 API")
@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService,
) {
    @PostMapping
    @Operation(
        summary = "post 생성",
        description = "현재 userId 로 게시물을 생성합니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "게시물 생성 성공")
        ]
    )
    fun createPost(
        memberRequest: MemberRequest,
        @RequestBody request: CreatePostRequest,
    ): ResponseEntity<PostResponse> {
        val post = postService.createPost(request.toCommand(memberRequest.userId))

        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.from(post))
    }
}
