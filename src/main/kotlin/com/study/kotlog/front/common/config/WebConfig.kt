package com.study.kotlog.front.common.config

import com.study.kotlog.front.common.support.AuthInterceptor
import com.study.kotlog.front.common.support.MemberRequestResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authInterceptor: AuthInterceptor,
    private val memberRequestResolver: MemberRequestResolver,
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns(
                "/api/v1/auth/signup",
                "/api/v1/auth/login",
                "/api/v1/auth/reissue",
                "/api/v1/redis/ping"
            )
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(memberRequestResolver)
    }
}
