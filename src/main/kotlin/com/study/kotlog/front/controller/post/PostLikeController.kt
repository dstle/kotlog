package com.study.kotlog.front.controller.post

import com.study.kotlog.domain.post.PostLikeService
import com.study.kotlog.front.common.web.MemberRequest
import com.study.kotlog.front.controller.post.dto.LikeStatusResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "/posts", description = "블로그 글 좋아요 API")
@RestController
@RequestMapping("/api/v1/posts")
class PostLikeController(
    private val postLikeService: PostLikeService,
) {
    @GetMapping("/{postId}/like/status")
    @Operation(
        summary = "like status 조회",
        description = "사용자가 게시물 좋아요 상태 확인",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "like status 조회 성공")
        ]
    )
    fun likeStatus(
        memberRequest: MemberRequest,
        @PathVariable postId: Long,
    ): LikeStatusResponse {
        val result = postLikeService.getLikeStatus(postId, memberRequest.userId)

        return LikeStatusResponse.from(result.isLiked, result.likeCount)
    }

    @PostMapping("/{postId}/like")
    @Operation(
        summary = "like 누르기",
        description = "사용자가 게시물 좋아요 누름",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "like status 조회 성공")
        ]
    )
    fun like(
        memberRequest: MemberRequest,
        @PathVariable postId: Long,
    ) {
        postLikeService.like(postId, memberRequest.userId)
    }

    @PostMapping("/{postId}/unlike")
    @Operation(
        summary = "like 삭제",
        description = "사용자가 게시물 좋아요 취소",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "like status 조회 성공")
        ]
    )
    fun unlike(
        memberRequest: MemberRequest,
        @PathVariable postId: Long,
    ) {
        postLikeService.unlike(postId, memberRequest.userId)
    }
}
