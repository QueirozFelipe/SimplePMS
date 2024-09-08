package dev.felipequeiroz.simplepms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.felipequeiroz.simplepms.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Object object) {
        var algorithm = Algorithm.HMAC256(secret);
        try {
            User user = (User) object;
            return JWT.create()
                    .withIssuer("dev.felipequeiroz.simplepms")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (ClassCastException | JWTCreationException ex) {
            throw new RuntimeException("Não foi possivel gerar o Token JWT", ex);
        }
    }

    public String getSubject(String JWTToken) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("dev.felipequeiroz.simplepms")
                    .build()
                    .verify(JWTToken)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token JWT não válido ou expirado");
        }
    }

    private Instant expirationDate() { return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); }


}
