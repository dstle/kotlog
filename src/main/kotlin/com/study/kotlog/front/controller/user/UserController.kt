package com.study.kotlog.front.controller.user

import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.front.common.web.MemberRequest
import com.study.kotlog.front.controller.user.dto.UserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "/users", description = "내 정보 API")
@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val userRepository: UserRepository,
) {
    @GetMapping("/me")
    @Operation(
        summary = "내 정보 조회",
        description = "userId 를 기반으로 사용자 정보를 조회합니다.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "내 정보 조회 성공")
        ]
    )
    fun me(memberRequest: MemberRequest): ResponseEntity<UserResponse> {
        val userId = memberRequest.userId
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("유저를 찾을 수 없습니다. id=$userId") }
        return ResponseEntity.ok(UserResponse.from(user))
    }
}
