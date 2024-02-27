package kr.co.demo.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.security.Key;

@Log4j2
public class JwtTokenProvider {
    private final Key key;

    public JwtTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JwtToken convertAuthToken(String token) {
        token = this.resolveToken(token);
        return new JwtToken(token, this.key);
    }

    public String resolveToken(String bearerToken) {
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : bearerToken;
    }
}
