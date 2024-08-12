package com.example.security.auditing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.handler.MappedInterceptor

@Configuration
class LogInterceptorFilter  {

    @Autowired
    lateinit var logInterceptor: LogInterceptor
    @Bean
    fun loginInter(): MappedInterceptor {
        return MappedInterceptor(null, logInterceptor)
    }
}
