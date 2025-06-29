package com.study.kotlog.front.controller

import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.front.controller.dto.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val userRepository: UserRepository,
) {

    @GetMapping("/me")
    fun me(userId: Long): ResponseEntity<UserResponse> {
        val user = userRepository.findById(userId).get()
        return ResponseEntity.ok(UserResponse.from(user))
    }
}
