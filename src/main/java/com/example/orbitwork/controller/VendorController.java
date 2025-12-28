package com.example.orbitwork.controller;

import com.example.orbitwork.dto.LoginRequest;
import com.example.orbitwork.dto.LoginResponse;
import com.example.orbitwork.dto.VendorDTO;
import com.example.orbitwork.service.VendorService;
import com.example.orbitwork.utils.JwtUtil;
import jakarta.validation.Valid;
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
@RequestMapping("/vendor")
public class VendorController {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final VendorService vendorService;

    private final AuthenticationManager authenticationManager;

    public VendorController(JwtUtil jwtUtil, UserDetailsService userDetailsService, VendorService vendorService, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.vendorService = vendorService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<Map<String, LoginResponse>> registerUser(
            @RequestBody @Valid VendorDTO vendorDTO
            ) {
        Map<String, LoginResponse> response = new HashMap<>();
        LoginResponse loginResponse = vendorService.register(vendorDTO);
        response.put("registerVendorData", loginResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, LoginResponse>> loginUser(
            @RequestBody @Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        LoginResponse req = vendorService.getVendor(request.getEmail());
        req.setToken(jwtUtil.generateToken(userDetails));

        Map<String, LoginResponse> response = new HashMap<>();
        response.put("vendorData", req);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
