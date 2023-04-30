package com.budgetfy.app.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Injecting values from properties files
    @Value("${jwt.token.ttl}")
    private Long ttl;

    @Value("${jwt.secretKey}")
    private String secretKey;

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     *
     * @return The username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the claim from the JWT token.
     *
     * @param token          The JWT token.
     * @param claimsResolver The function to resolve the claim.
     * @param <T>            The type of the claim.
     *
     * @return The claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the specified user details.
     *
     * @param userDetails The user details.
     *
     * @return The generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for the specified user details and extra claims.
     *
     * @param extraClaims The extra claims.
     * @param userDetails The user details.
     *
     * @return The generated JWT token.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ttl))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates if the specified JWT token is valid for the specified user details.
     *
     * @param token       The JWT token.
     * @param userDetails The user details.
     *
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    /**
     * Checks if the specified JWT token is expired.
     *
     * @param token The JWT token.
     *
     * @return True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token.
     *
     * @return The expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     *
     * @return The claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Returns a new HMAC-SHA key based on the JWT secret key specified in the application properties file.
     * The key is derived from the secret key by base64 decoding it and then creating a new HMAC-SHA key instance
     * using the resulting byte array.
     *
     * @return a new HMAC-SHA key instance derived from the JWT secret key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
