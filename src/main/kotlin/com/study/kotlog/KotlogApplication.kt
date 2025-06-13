package com.study.kotlog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlogApplication

fun main(args: Array<String>) {
    runApplication<KotlogApplication>(*args)
}
