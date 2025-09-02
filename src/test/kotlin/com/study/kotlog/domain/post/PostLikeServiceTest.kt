package com.study.kotlog.domain.post

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate

@SpringBootTest
class PostLikeServiceTest(
    val postLikeService: PostLikeService,
    val likeStateStore: LikeStateStore,
    val likeCountStore: LikeCountStore,
    val redis: StringRedisTemplate,
) : FunSpec({
    beforeTest {
        redis.connectionFactory?.connection?.serverCommands()?.flushAll()
    }

    test("게시물의 좋아요 상태 조회 성공") {
        likeCountStore.increment(1)
        val increment = likeCountStore.increment(1)

        likeStateStore.like(1, 1)

        val pair = postLikeService.getLikeStatus(1, 1)

        pair.first shouldBe true
        pair.second shouldBe increment
    }

    test("사용자가 좋아요 누르기 성공") {
        postLikeService.like(1, 1)

        val likeStatus = postLikeService.getLikeStatus(1, 1)

        likeStatus.first shouldBe true
        likeStatus.second shouldBe 1
    }

    test("사용자가 좋아요 중복으로 눌러서 좋아요 증가 반영 안됨") {
        postLikeService.like(1, 1)
        postLikeService.like(1, 1)

        val likeStatus = postLikeService.getLikeStatus(1, 1)

        likeStatus.first shouldBe true
        likeStatus.second shouldBe 1
    }

    test("사용자가 좋아요 취소 성공") {
        postLikeService.like(1, 1)
        postLikeService.unlike(1, 1)

        val likeStatus = postLikeService.getLikeStatus(1, 1)

        likeStatus.first shouldBe false
        likeStatus.second shouldBe 0
    }
})
