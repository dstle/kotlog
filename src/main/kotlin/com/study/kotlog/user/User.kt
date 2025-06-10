package com.study.kotlog.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_name", nullable = false)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime
)
