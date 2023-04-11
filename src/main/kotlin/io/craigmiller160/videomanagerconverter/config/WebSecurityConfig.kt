package io.craigmiller160.videomanagerconverter.config

import io.craigmiller160.videomanagerconverter.security.JwtAuthConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebFluxSecurity
class WebSecurityConfig(private val jwtAuthConverter: JwtAuthConverter) {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
      http
          .csrf()
          .disable()
          .oauth2ResourceServer { it.jwt().jwtAuthenticationConverter(jwtAuthConverter) }
          .authorizeHttpRequests {
              it.requestMatchers("/**").hasAuthority("access")
          }
          .build()
}
