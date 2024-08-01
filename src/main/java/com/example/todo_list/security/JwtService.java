package com.example.todo_list.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    public static final String JWT_SECRET ="6nKP7aMTr7zTxhE6VgHsjqLc3BfZs8Xs" ;


    private Claims getAllClaimsFromToken(String token) {
        final var key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final var claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationFromToken(String token) {
        return this.getClaimsFromToken(token, claims -> claims.getExpiration());
    }

    private boolean isTokenExpiration(String token) {
        final var expirationDate = this.getExpirationFromToken(token);
        return expirationDate.before(new Date());
    }


    public String getUsernameFromToken(String token) {
        return this.getClaimsFromToken(token, claims -> claims.getSubject());
    }

    private String createToken(Map<String,Object> claims, String subject)
    {
        final var key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +JWT_TOKEN_VALIDITY *1000 ) )
                .signWith(key)
                .compact();

    }


    public String generateToken(UserDetails userDetails)
    {
        final Map<String,Object> claims = Collections.singletonMap("ROLES",userDetails.getAuthorities().toString());
        return this.createToken(claims,userDetails.getUsername());

    }


    public Boolean validateToken(String token,UserDetails userDetails)
    {
        final var usernameFromUserDetails =userDetails.getUsername();
        final String usernameFromToken =this.getUsernameFromToken(token);
        return usernameFromUserDetails.equals(usernameFromToken) &&   !this.isTokenExpiration(token);
    }








}
