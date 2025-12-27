package com.example.orbitwork.controller;

import com.example.orbitwork.dto.LoginRequest;
import com.example.orbitwork.dto.LoginResponse;
import com.example.orbitwork.dto.UserDTO;
import com.example.orbitwork.service.UserService;
import com.example.orbitwork.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public UserController(JwtUtil jwtUtil, UserDetailsService userDetailsService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<Map<String, LoginResponse>> registerUser(
            @RequestBody UserDTO userDTO
            ) {
        Map<String, LoginResponse> response = new HashMap<>();
        LoginResponse loginResponse = userService.register(userDTO);
        response.put("registerUserData", loginResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, LoginResponse>> loginUser(
            @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        LoginResponse req = userService.getUser(request.getEmail());
        req.setToken(jwtUtil.generateToken(userDetails));

        Map<String, LoginResponse> response = new HashMap<>();
        response.put("userData", req);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
