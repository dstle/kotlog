package com.study.kotlog.front.controller.auth.dto

import com.study.kotlog.domain.auth.dto.SignupCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원가입 요청 DTO")
data class SignupRequest(

    @Schema(description = "사용자 id", example = "dustle1313")
    val username: String,

    @Schema(description = "사용자 password", example = "securePass123!")
    val password: String,

    @Schema(description = "사용자 email", example = "asdf@gmail.com")
    val email: String,

    @Schema(description = "사용자 nickname", example = "dustle")
    val nickname: String,
) {
    fun toCommand() = SignupCommand(
        username = this.username,
        password = this.password,
        email = this.email,
        nickname = this.nickname
    )
}
