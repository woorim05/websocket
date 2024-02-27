package kr.co.demo.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class Config {

    private String secretKeyPlain = "jwtSecretKeyjwtSecretKeyjwtSecretKeyjwtSecretKeyjwtSecretKeyjwtSecretKeyjwtSecretKeyjwtSecretKeyjwtSecretKeyjwtSecretKey";
    private String secret = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secret);
    }
}
