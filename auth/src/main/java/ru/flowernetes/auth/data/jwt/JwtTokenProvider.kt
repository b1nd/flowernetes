package ru.flowernetes.auth.data.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
open class JwtTokenProvider(
  @Value("\${app.jwtSecret}")
  private val jwtSecret: String,
  @Value("\${app.jwtExpirationInMs}")
  private val jwtExpirationInMs: Int = 0
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun generateToken(username: String): String {

        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs)

        return Jwts.builder()
          .setSubject(username)
          .setIssuedAt(now)
          .setExpiration(expiryDate)
          .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)), SignatureAlgorithm.HS512)
          .compact()
    }

    fun getUsernameFromToken(token: String?): String {
        return Jwts.parserBuilder()
          .setSigningKey(jwtSecret)
          .build()
          .parseClaimsJws(token)
          .body.subject
    }

    fun validateToken(authToken: String?): Boolean = runCatching {
        Jwts.parserBuilder()
          .setSigningKey(jwtSecret)
          .build()
          .parseClaimsJws(authToken)
    }.onFailure {
        log.error(it.message, it.stackTrace)
    }.isSuccess

}