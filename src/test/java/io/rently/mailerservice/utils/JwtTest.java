package io.rently.mailerservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.rently.mailerservice.errors.Errors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtTest {

    public Jwt jwt;
    public static final String SECRET = "secret";
    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS384;
    public static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(SECRET.getBytes(), ALGORITHM.getJcaName());

    @BeforeEach
    void setup() {
        this.jwt = new Jwt(SECRET, ALGORITHM);
    }

    @Test
    void invalidAlgo_agrException() {
        SignatureAlgorithm jwtAlgo = SignatureAlgorithm.ES256;

        assertThrows(IllegalArgumentException.class, () -> new Jwt(SECRET, jwtAlgo));
    }

    @Test
    void invalidSecret_agrException() {
        String jwtSecret = "";

        assertThrows(IllegalArgumentException.class, () -> new Jwt(jwtSecret, ALGORITHM));
    }

    @Test
    void generateBearerToken() {
        String token = jwt.generateBearToken();

        assert token.matches("(^[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*$)");
    }

    @Test
    void validateBearerToken_malformedToken_malformedException() {
        String token = "malformed token";

        assertThrows(Errors.MALFORMED_TOKEN.getClass(), () -> jwt.validateBearerToken(token));
    }

    @Test
    void validateBearerToken_invalidSecret_unauthorizedException() {
        Date validDate = new Date(System.currentTimeMillis() - 60000L);
        String invalidSecret = "invalid secret";
        String token = Jwts.builder()
                .setIssuedAt(validDate)
                .setExpiration(validDate)
                .signWith(ALGORITHM, invalidSecret)
                .compact();
        String bearer = "Bearer " + token;

        assertThrows(Errors.UNAUTHORIZED_REQUEST.getClass(), () -> jwt.validateBearerToken(bearer));
    }

    @Test
    void validateBearerToken_invalidAlgo_unauthorizedException() {
        Date validDate = new Date(System.currentTimeMillis() - 60000L);
        SignatureAlgorithm invalidAlgo = SignatureAlgorithm.HS384;
        String token = Jwts.builder()
                .setIssuedAt(validDate)
                .setExpiration(validDate)
                .signWith(invalidAlgo, SECRET_KEY_SPEC)
                .compact();
        String bearer = "Bearer " + token;

        assertThrows(Errors.UNAUTHORIZED_REQUEST.getClass(), () -> jwt.validateBearerToken(bearer));
    }

    @Test
    void validateBearerToken_expiredToken_tokenExpiredException() {
        Date pastDate = new Date(System.currentTimeMillis() - 60000L);
        String token = Jwts.builder()
                .setIssuedAt(pastDate)
                .setExpiration(pastDate)
                .signWith(ALGORITHM, SECRET_KEY_SPEC)
                .compact();
        String bearer = "Bearer " + token;

        assertThrows(Errors.EXPIRED_TOKEN.getClass(), () -> jwt.validateBearerToken(bearer));
    }

    @Test
    void validateBearerToken_validToken() {
        Date validDate = new Date(System.currentTimeMillis() + 60000L);
        String token = Jwts.builder()
                .setIssuedAt(validDate)
                .setExpiration(validDate)
                .signWith(ALGORITHM, SECRET_KEY_SPEC)
                .compact();
        String bearer = "Bearer " + token;

        assert jwt.validateBearerToken(bearer);
    }

    @Test
    void getClaims_returnsClaims() {
        Date validDate = new Date(System.currentTimeMillis() + 60000L);
        String token = Jwts.builder()
                .setIssuedAt(validDate)
                .setExpiration(validDate)
                .signWith(ALGORITHM, SECRET_KEY_SPEC)
                .compact();

        Claims claims = jwt.getClaims(token);

        assert Objects.equals(new Date(claims.getExpiration().getTime()).toString(), validDate.toString());
        assert Objects.equals(new Date(claims.getIssuedAt().getTime()).toString(), validDate.toString());
    }
}