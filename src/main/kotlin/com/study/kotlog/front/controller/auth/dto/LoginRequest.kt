package com.study.kotlog.front.controller.auth.dto

import com.study.kotlog.domain.auth.dto.LoginCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 요청 DTO")
data class LoginRequest(
    @Schema(description = "사용자 id", example = "dustle1313")
    val username: String,
    @Schema(description = "사용자 password", example = "securePass123!")
    val password: String,
) {
    fun toCommand() = LoginCommand(
        username = this.username,
        password = this.password
    )
}
