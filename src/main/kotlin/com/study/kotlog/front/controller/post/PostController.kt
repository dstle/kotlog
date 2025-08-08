package com.study.kotlog.front.controller.post

import com.study.kotlog.domain.post.PostService
import com.study.kotlog.front.common.web.MemberRequest
import com.study.kotlog.front.controller.post.dto.CreatePostRequest
import com.study.kotlog.front.controller.post.dto.PostResponse
import com.study.kotlog.front.controller.post.dto.UpdatePostRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/{postId}")
    @Operation(
        summary = "post 단건 조회",
        description = "postId로 게시물을 가져옵니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "게시물 가져오기 성공")
        ]
    )
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostResponse> {
        val post = postService.getPost(postId)

        return ResponseEntity.status(HttpStatus.OK).body(PostResponse.from(post))
    }

    @GetMapping("/posts")
    @Operation(
        summary = "post 다건 조회",
        description = "키워드로 게시물을 다건 조회합니다. 기본 값은 최신순 10 개를 가져옵니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "게시물 가져오기 성공")
        ]
    )
    fun getPosts(
        @RequestParam(required = false) keyword: String?,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): Page<PostResponse> = postService.getPosts(keyword, pageable)
        .map { PostResponse.from(it) }

    @PutMapping("/{postId}")
    @Operation(
        summary = "post 수정",
        description = "게시물을 수정합니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "게시물 수정 성공")
        ]
    )
    fun updatePost(
        memberRequest: MemberRequest,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest,
    ): ResponseEntity<PostResponse> {
        val updatePost = postService.updatePost(request.toCommand(memberRequest.userId, postId))

        return ResponseEntity.status(HttpStatus.OK).body(PostResponse.from(updatePost))
    }

    @DeleteMapping("/{postId}")
    @Operation(
        summary = "post 삭제",
        description = "게시물을 삭제합니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "게시물 삭제 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "게시물 없음")
        ]
    )
    fun deletePost(
        memberRequest: MemberRequest,
        @PathVariable postId: Long,
    ): ResponseEntity<Void> {
        postService.deletePost(memberRequest.userId, postId)

        return ResponseEntity.noContent().build()
    }
}
