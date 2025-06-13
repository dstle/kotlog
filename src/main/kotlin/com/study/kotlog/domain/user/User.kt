package com.study.kotlog.domain.user

import com.study.kotlog.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Column(name = "user_name", nullable = false)
    val username: String,
    @Column(name = "password", nullable = false)
    var password: String,
    @Column(name = "email", nullable = false)
    var email: String,
    @Column(name = "nickname", nullable = false)
    var nickname: String,
) : BaseEntity()
