package com.study.kotlog.front.controller.user

import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.front.controller.user.dto.UserResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userRepository: UserRepository,
) {

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "userId 를 기반으로 사용자 정보를 조회합니다.")
    fun me(userId: Long): ResponseEntity<UserResponse> {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("유저를 찾을 수 없습니다. id=$userId") }

        return ResponseEntity.ok(UserResponse.Companion.from(user))
    }
}
