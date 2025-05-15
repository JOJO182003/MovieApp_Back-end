package com.movieapp.api.rest.controller;

import com.movieapp.api.rest.dto.request.LoginRequest;
import com.movieapp.api.rest.dto.response.JwtResponse;
import com.movieapp.api.rest.dto.response.UserInfoResponse;
import com.movieapp.domain.model.CustomUserDetails;
import com.movieapp.security.JwtProvider;
import com.movieapp.security.SecurityContextUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }


    @GetMapping("/me")
    public ResponseEntity<?> currentUser() {
        CustomUserDetails user = SecurityContextUtil.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        return ResponseEntity.ok(new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getAuthorities().stream()
                        .findFirst()
                        .map(Object::toString)
                        .orElse(null)
        ));
    }
}
