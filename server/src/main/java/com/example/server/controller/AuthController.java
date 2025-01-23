package com.example.server.controller;

import com.example.server.model.dto.UserAuthDto;
import com.example.server.repository.UserRepository;
import com.example.server.service.JwtService;
import com.example.server.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserAuthDto userAuthDto) {
        try {
            var user = userService.authenticate(userAuthDto).getData();
            return ResponseEntity.ok(Map.of(
                    "accessToken", jwtService.generateToken(user),
                    "refreshToken", jwtService.generateRefreshToken(user)
            ));
        } catch (UsernameNotFoundException e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserAuthDto userAuthDto) {
        try {
            userService.register(userAuthDto);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (IllegalArgumentException e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "An unexpected error occurred");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        try {
            var accessToken = userService.refreshAccessToken(request.get("refreshToken")).getData();
            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken
            ));
        } catch (UsernameNotFoundException | JwtException e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    private ResponseEntity<Map<String, String>> createErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("error", message));
    }

}
