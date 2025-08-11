package com.study.kotlog.front.controller.comment

import com.study.kotlog.domain.comment.CommentService
import com.study.kotlog.front.common.web.MemberRequest
import com.study.kotlog.front.controller.comment.dto.CommentResponse
import com.study.kotlog.front.controller.comment.dto.CreateCommentRequest
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

}
