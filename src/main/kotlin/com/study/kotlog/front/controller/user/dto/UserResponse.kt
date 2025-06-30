package com.study.kotlog.front.controller.user.dto

import com.study.kotlog.domain.user.User
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내 정보를 가져올 때의 응답 DTO")
data class UserResponse(
    @Schema(description = "사용자 id", example = "dustle1313")
    val username: String,

    @Schema(description = "사용자 email", example = "asdf@gmail.com")
    val email: String,

    @Schema(description = "사용자 nickname", example = "dustle")
    val nickname: String,
) {
    companion object {
        fun from(user: User): UserResponse = UserResponse(username = user.username, email = user.email, nickname = user.nickname)
    }
}
