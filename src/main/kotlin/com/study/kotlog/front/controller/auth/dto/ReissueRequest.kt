package com.study.kotlog.front.controller.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 재발행 요청 DTO")
data class ReissueRequest(
    @Schema(description = "refresh 토큰", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUyMjQyNTc0LCJleHAiOjE3NTM0NTIxNzR9.VzmwvGgKvqMHSvkqafZTIX5L5EcmR28AvFvDeSNgjEx0lzZP9KqFhEdDahsm1WL")
    val refreshToken: String,
)
