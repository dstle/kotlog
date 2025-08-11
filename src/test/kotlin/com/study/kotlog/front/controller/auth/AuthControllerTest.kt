package com.study.kotlog.front.controller.auth

import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.front.controller.auth.dto.SignupRequest
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest(
    val mockMvc: MockMvc,
    val userRepository: UserRepository,
) : FunSpec({
    test("POST /api/v1/auth/signup 요청이 들어왔을 때 200 OK") {
        val signupRequest = SignupRequest(
            username = "dustle13",
            password = "securePass123!",
            email = "dustle@example.com",
            nickname = "Dustle"
        )

        val requestBody = """
            {
              "username": "${signupRequest.username}",
              "password": "${signupRequest.password}",
              "email": "${signupRequest.email}",
              "nickname": "${signupRequest.nickname}"
            }
        """.trimIndent()

        mockMvc.post("/api/v1/auth/signup") {
            contentType = org.springframework.http.MediaType.APPLICATION_JSON
            content = requestBody
        }.andDo { print() }
            .andExpect {
                status { isCreated() }
            }
    }
})
