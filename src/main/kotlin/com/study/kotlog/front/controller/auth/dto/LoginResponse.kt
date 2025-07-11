package com.study.kotlog.front.controller.auth.dto

import com.study.kotlog.domain.auth.dto.LoginResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 결과 DTO")
data class LoginResponse(
    @Schema(description = "access 토큰", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUyMjQyNTc0LCJleHAiOjE3NTIyNDQzNzR9.Aamh96l3QuoLoZualaGg2erUFWMzU5FQWyIOaywyVaInw1pHGrZIHIUgMD4HHlh")
    val accessToken: String,
    @Schema(description = "refresh 토큰", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUyMjQyNTc0LCJleHAiOjE3NTM0NTIxNzR9.VzmwvGgKvqMHSvkqafZTIX5L5EcmR28AvFvDeSNgjEx0lzZP9KqFhEdDahsm1WL")
    val refreshToken: String,
    @Schema(description = "access 토큰 기한", example = "1800")
    val expiresIn: Long,
    @Schema(description = "토큰 타입", example = "Bearer")
    val tokenType: String = "Bearer",
) {
    companion object {
        fun from(result: LoginResult) = LoginResponse(
            accessToken = result.accessToken,
            refreshToken = result.refreshToken,
            expiresIn = result.expiresIn
        )
    }
}
