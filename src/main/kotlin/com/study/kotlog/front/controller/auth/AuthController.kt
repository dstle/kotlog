package com.study.kotlog.front.controller.auth

import com.study.kotlog.domain.auth.AuthService
import com.study.kotlog.front.controller.auth.dto.LoginRequest
import com.study.kotlog.front.controller.auth.dto.LoginResponse
import com.study.kotlog.front.controller.auth.dto.ReissueRequest
import com.study.kotlog.front.controller.auth.dto.SignupRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "/auth", description = "인증 API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/signup")
    @Operation(
        summary = "회원 가입",
        description = "사용자로부터 회원 정보를 입력받아 계정을 생성합니다. 중복된 사용자명은 가입할 수 없습니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "가입 성공")
        ]
    )
    fun signUp(@RequestBody request: SignupRequest): ResponseEntity<Void> {
        authService.signUp(request.toCommand())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/login")
    @Operation(
        summary = "로그인",
        description = "username 과 password 로 로그인하고 JWT token 을 반환합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "로그인 성공")
        ]
    )
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val result = authService.login(request.toCommand())

        return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.from(result))
    }

    @PostMapping("/reissue")
    @Operation(
        summary = "재발행",
        description = "JWT token 의 기간이 만료되었을 경우 refresh token 을 확인 후 재발행 합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "재발행 성공")
        ]
    )
    fun reissue(@RequestBody request: ReissueRequest): ResponseEntity<LoginResponse> {
        val result = authService.reissue(request.refreshToken)

        return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.from(result))
    }
}
