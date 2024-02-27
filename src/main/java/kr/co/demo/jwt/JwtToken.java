package kr.co.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.security.Key;

@Log4j2
@RequiredArgsConstructor
public class JwtToken {
    @Getter
    private final String token;
    private final Key key;

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return (Claims) Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(this.token).getBody();
        } catch (Exception e) {
            log.info("JWT token Exception : " + e.getMessage());
        }
        return null;
    }

}
