package com.study.kotlog

import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest(
    private val userRepository: UserRepository,
) : DescribeSpec({
    describe("UserRepository test") {
        it("User 저장 성공") {
            val user =
                User(
                    username = "dustle",
                    password = "111",
                    email = "111",
                    nickname = "111"
                ).also {
                    userRepository.save(it)
                }

            val result = userRepository.findById(user.id).get()

            result.also {
                it.username shouldBe user.username
                it.password shouldBe user.password
                it.email shouldBe user.email
                it.nickname shouldBe user.nickname
                it.id shouldNotBe null
                it.createdAt shouldNotBe null
                it.updatedAt shouldNotBe null
            }
        }
    }
})
