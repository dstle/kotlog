package com.study.kotlog.domain.auth.dto

data class LoginCommand(
    val username: String,
    val password: String,
)
