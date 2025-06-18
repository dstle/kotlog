package com.study.kotlog.domain

import com.study.kotlog.util.ZonedDateTimeConverter
import jakarta.persistence.*
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val _id: Long? = null
    val id: Long
        get() = _id!!

    @Column(name = "created_at", nullable = false, updatable = false)
    @Convert(converter = ZonedDateTimeConverter::class)
    var createdAt: ZonedDateTime = ZonedDateTime.now()

    @Column(name = "updated_at", nullable = false)
    @Convert(converter = ZonedDateTimeConverter::class)
    var updatedAt: ZonedDateTime = ZonedDateTime.now()

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = ZonedDateTime.now()
    }
}
