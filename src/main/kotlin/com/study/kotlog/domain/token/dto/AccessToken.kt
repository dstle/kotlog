package com.study.kotlog.domain.token.dto

data class AccessToken(
    val token: String,
    val expiresIn: Long,
)
