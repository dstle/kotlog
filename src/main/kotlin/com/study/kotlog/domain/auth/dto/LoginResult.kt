package com.study.kotlog.domain.auth.dto

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)
