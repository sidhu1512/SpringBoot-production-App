package com.example.fitness.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private String jwtSecret = "865432qwe8763245tyui9876543wertyui987654321qwertyuiop0987654321";
    private int jwtExpirationMs = 1728000000;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    public String generateToken(String userID, String role){

        return Jwts.builder()
                .subject(userID)
                .claim("roles", Collections.singletonList(role))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(Key())
                .compact();
    }

    public boolean validateJwtToken(String jwtToken){
        try{
            Jwts.parser().verifyWith((SecretKey) Key()).build()
                    .parseSignedClaims(jwtToken);
        } catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private Key Key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserIdFromToken(String jwt){
        return Jwts.parser().verifyWith((SecretKey) Key())
                .build().parseSignedClaims(jwt)
                .getPayload().getSubject();
    }

    public Claims getAllClaims(String jwt){
       return Jwts.parser().verifyWith((SecretKey) Key())
               .build().parseSignedClaims(jwt).getPayload();
    }

}
