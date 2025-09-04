package com.study.kotlog.front.controller.post.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Post Like 상태 조회 DTO")
data class LikeStatusResponse(
    @Schema(description = "좋아요 여부", example = "true")
    val isLiked: Boolean,
    @Schema(description = "게시물 총 like 수", example = "150L")
    val totalLikes: Long,
) {
    companion object {
        fun from(
            isLiked: Boolean,
            totalLikes: Long,
        ) = LikeStatusResponse(
            isLiked = isLiked,
            totalLikes = totalLikes
        )
    }
}
