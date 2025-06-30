package com.study.kotlog.front.controller.auth

import com.study.kotlog.domain.auth.AuthService
import com.study.kotlog.front.controller.auth.dto.SignupRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignupRequest): ResponseEntity<Void> {
        authService.signUp(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
