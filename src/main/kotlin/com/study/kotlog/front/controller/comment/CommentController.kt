package com.study.kotlog.front.controller.comment

import com.study.kotlog.domain.comment.CommentService
import com.study.kotlog.front.common.web.MemberRequest
import com.study.kotlog.front.controller.comment.dto.CommentResponse
import com.study.kotlog.front.controller.comment.dto.CreateCommentRequest
import com.study.kotlog.front.controller.comment.dto.UpdateCommentRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "/comments", description = "블로그 댓글 API")
@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping
    @Operation(
        summary = "comment 생성",
        description = "게시글에 댓글을 답니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "댓글 생성 성공")
        ]
    )
    fun createComment(
        memberRequest: MemberRequest,
        @RequestBody request: CreateCommentRequest,
    ) {
        commentService.createComment(request.toCommand(memberRequest.userId))
    }

    @GetMapping
    @Operation(
        summary = "comment 다건 조회",
        description = "postId 로 조회되는 모든 댓글을 가져옵니다",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "댓글 가져오기 성공")
        ]
    )
    fun getComments(@RequestParam postId: Long): List<CommentResponse> {
        val commentResults = commentService.getComments(postId)

        return commentResults.map { CommentResponse.from(it) }
    }

    @DeleteMapping("/{commentId}")
    @Operation(
        summary = "comment 삭제",
        description = "작성한 댓글을 삭제합니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "댓글 삭제 성공")
        ]
    )
    fun deleteComment(
        @RequestParam commentId: Long,
        memberRequest: MemberRequest,
    ) {
        commentService.deleteComment(commentId, memberRequest.userId)
    }

    @PutMapping("/{commentId}")
    @Operation(
        summary = "comment 수정",
        description = "작성한 댓글을 수정합니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "댓글 수정 성공")
        ]
    )
    fun updateComment(
        @RequestParam commentId: Long,
        memberRequest: MemberRequest,
        @RequestBody request: UpdateCommentRequest,
    ) {
        commentService.updateComment(request.toCommand(memberRequest.userId, commentId))
    }
}
