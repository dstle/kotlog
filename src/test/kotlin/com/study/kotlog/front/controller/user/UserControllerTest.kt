package com.study.kotlog.front.controller.user

import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.util.JwtUtil
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest(
    val mockMvc: MockMvc,
    val jwtUtil: JwtUtil,
    val userRepository: UserRepository,
) : FunSpec({

    test("GET /api/v1/users/me 요청이 들어왔을 때 200 OK 반환해야 함") {
        val user = User(
            username = "dustle15",
            password = "1111111111111",
            email = "111",
            nickname = "111"
        ).also {
            userRepository.saveAndFlush(it)
        }

        val token = jwtUtil.generateToken(user.id, 60 * 1000L)

        mockMvc.get("/api/v1/users/me") {
            header("Authorization", "Bearer $token")
        }.andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.username") { value(user.username) }
                jsonPath("$.email") { value(user.email) }
                jsonPath("$.nickname") { value(user.nickname) }
            }
    }
})
