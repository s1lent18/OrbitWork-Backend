package com.example.orbitwork.controller;

import com.example.orbitwork.dto.ApiResponse;
import com.example.orbitwork.dto.SpaceDTO;
import com.example.orbitwork.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/space")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<SpaceDTO>> addSpace(
            @Valid @RequestBody SpaceDTO spaceDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        var response = spaceService.registerSpace(spaceDTO, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Space added successfully", response));
    }
}
