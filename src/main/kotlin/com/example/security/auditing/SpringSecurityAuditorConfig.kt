package com.example.security.auditing

import com.example.security.auth.permission.UserPrinciple
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class SpringSecurityAuditorConfig {

    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return SpringSecurityAuditorAwareImp()
    }
}

class SpringSecurityAuditorAwareImp : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        try {
            val auth: UserPrinciple = SecurityContextHolder.getContext().authentication?.principal as UserPrinciple
            return Optional.of(auth.getId())

        }catch (_:Exception){
        }
        return Optional.empty()
    }
}
