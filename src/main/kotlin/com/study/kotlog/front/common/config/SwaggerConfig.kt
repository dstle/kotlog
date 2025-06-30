package com.study.kotlog.front.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "kotlog",
        description = "코프링으로 블로그 만들기",
        version = "v1.0"
    )
)

@Configuration
class SwaggerConfig
