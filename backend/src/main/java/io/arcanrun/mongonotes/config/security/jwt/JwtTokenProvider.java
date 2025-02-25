package io.arcanrun.mongonotes.config.security.jwt;

import io.arcanrun.mongonotes.entity.User;
import io.arcanrun.mongonotes.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
  private static final String AUTHORITIES_CLAIM = "authorities";
  private static final String USERNAME_CLAIM = "username";

  @Value("${vue-spring-boot-mongo-template.jwt.token.secret}")
  private final String secret;

  @Value("${vue-spring-boot-mongo-template.jwt.token.validity-period}")
  private final Integer validityInSeconds;

  private final UserDetailsService userDetailsService;

  public String createTokenFor(User user) {
    return doCreateToken(user.getId(), user.getUsername(), user.getAuthorities());
  }

  public Authentication buildAuthentication(@NonNull String token) {
    var claims = parseAndValidateToken(token);
    var user = userDetailsService.loadUserByUsername(claims.get(USERNAME_CLAIM).toString());

    return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
  }

  private String doCreateToken(
      ObjectId userId, String username, List<GrantedAuthority> grantedAuthorities) {
    return Jwts.builder()
        .subject(userId.toString())
        .claim(USERNAME_CLAIM, username)
        .claim(AUTHORITIES_CLAIM, grantedAuthorities)
        .issuedAt(new Date())
        .expiration(
            new Date(TimeUnit.SECONDS.toMillis(validityInSeconds) + System.currentTimeMillis()))
        .signWith(buildSecretKey(), Jwts.SIG.HS512)
        .compact();
  }

  private SecretKey buildSecretKey() {
    var bytesFromSecret = Decoders.BASE64.decode(secret);

    return Keys.hmacShaKeyFor(bytesFromSecret);
  }

  private Claims parseAndValidateToken(String token) {
    Claims claims;

    try {
      claims =
          Jwts.parser().verifyWith(buildSecretKey()).build().parseSignedClaims(token).getPayload();
    } catch (ExpiredJwtException e) {
      log.error(e.getMessage(), e);

      throw new UnauthorizedException("Expired or invalid JWT token");
    } catch (Exception e) {
      throw new UnauthorizedException("Invalid JWT token");
    }

    return claims;
  }
}
