package ru.flowernetes.auth.data.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import ru.flowernetes.auth.data.CustomUserDetailsService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
open class JwtAuthenticationFilter(
  private val tokenProvider: JwtTokenProvider,
  private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
      request: HttpServletRequest,
      response: HttpServletResponse,
      filterChain: FilterChain
    ) {
        try {
            val token = getTokenFromRequest(request)

            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                val username = tokenProvider.getUsernameFromToken(token)
                val userDetails = customUserDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: Exception) {
        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String {
        val bearerToken = request.getHeader("Authorization")
        val bearerTokenPrefix = "Bearer "

        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearerTokenPrefix)) {
            bearerToken.substring(bearerTokenPrefix.length, bearerToken.length)
        } else throw RuntimeException("No auth token provided")
    }
}