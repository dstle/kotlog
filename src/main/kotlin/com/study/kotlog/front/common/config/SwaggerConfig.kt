package com.study.kotlog.front.common.config

import com.study.kotlog.front.common.web.MemberRequest
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityScheme.Type
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "kotlog",
        version = "v1.0",
        description = "코틀린으로 블로그 만들기"
    )
)
class SwaggerConfig : WebMvcConfigurer {

    init {
        SpringDocUtils.getConfig()
            .addRequestWrapperToIgnore(MemberRequest::class.java)
    }

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(
            Components().addSecuritySchemes
                ("Bearer Authentication", createAPIKeyScheme())
        )

    fun createAPIKeyScheme(): SecurityScheme? = SecurityScheme().type(Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer")
}
