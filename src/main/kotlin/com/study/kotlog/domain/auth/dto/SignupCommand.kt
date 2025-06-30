package com.study.kotlog.domain.auth.dto

data class SignupCommand(
    val username: String,

    val password: String,

    val email: String,

    val nickname: String,
)
