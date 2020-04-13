package ru.flowernetes.auth.data.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.flowernetes.auth.data.CustomUserDetailsService
import ru.flowernetes.auth.data.jwt.JwtAuthenticationEntryPoint
import ru.flowernetes.auth.data.jwt.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
open class WebSecurityConfig(
  private val passwordEncoder: PasswordEncoder,
  private val tokenAuthFilter: JwtAuthenticationFilter,
  private val customUserDetailsService: CustomUserDetailsService,
  private val unauthorizedHandler: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
          .csrf().disable()
          .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
          .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .cors()
          .and()
          .addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
          .authorizeRequests()
          .antMatchers("/api/login", "/api/signup").permitAll()
          .antMatchers("/api/**").authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth ?: return

        auth.userDetailsService(customUserDetailsService)
          .passwordEncoder(passwordEncoder)
    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource? = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            allowedOrigins = listOf(CorsConfiguration.ALL)
            allowedHeaders = listOf(CorsConfiguration.ALL)
            maxAge = 1800L

            allowedMethods = listOf(
              HttpMethod.DELETE.name,
              HttpMethod.GET.name,
              HttpMethod.HEAD.name,
              HttpMethod.PATCH.name,
              HttpMethod.POST.name,
              HttpMethod.PUT.name
            )
        })
    }
}
