package org.mahata.spring_boot_oidc_sample

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests {
                it
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it.defaultSuccessUrl("http://localhost:8080/demo")
            }

        return http.build()
    }
}
