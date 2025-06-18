package com.study.kotlog

import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest(
    val userRepository: UserRepository,
) : FunSpec({

    context("User 저장") {
        test("저장 성공") {
            val user = User(
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

    context("PreUpdate 확인") {
        test("PreUpdate 는 값 변경 후 flush 후에 호출됨") {
            val user = User(
                username = "dustle",
                password = "111",
                email = "111",
                nickname = "111"
            ).also {
                userRepository.save(it)
            }

            val saved = userRepository.findById(user.id).get()
            val oldUpdatedAt = saved.updatedAt

            saved.nickname = "updated"
            saved.updatedAt shouldBe oldUpdatedAt

            userRepository.saveAndFlush(saved)

            val savedAfterUpdate = userRepository.findById(user.id).get()
            savedAfterUpdate.updatedAt.isAfter(oldUpdatedAt).shouldBeTrue()
        }
    }
})
