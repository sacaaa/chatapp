package com.example.server.service;

import com.example.server.model.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractEmail(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User userDetails);

    String generateToken(Map<String, Object> extraClaims, User userDetails);

    String generateRefreshToken(User userDetails);

    boolean isTokenValid(String token, User userDetails);

}
