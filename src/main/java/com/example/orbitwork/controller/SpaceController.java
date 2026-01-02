package com.example.orbitwork.controller;

import com.example.orbitwork.dto.ApiResponse;
import com.example.orbitwork.dto.SpaceDTO;
import com.example.orbitwork.dto.UpdateSpaceDTO;
import com.example.orbitwork.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping
    public ResponseEntity<Page<SpaceDTO>> getSpaces(
            @RequestParam() String location,
            Pageable pageable
    ) {
        return ResponseEntity.ok(spaceService.getSpaces(pageable, location));
    }

    @GetMapping("/vendor/{vendorId}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Page<SpaceDTO>> getVendorSpaces(
            @PathVariable Long vendorId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(spaceService.getVendorSpaces(pageable, vendorId));
    }

    @PatchMapping("/{spaceId}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<SpaceDTO>> updateSpace(
            @PathVariable Long spaceId,
            @RequestBody UpdateSpaceDTO updateDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        SpaceDTO updatedSpace = spaceService.updateSpace(spaceId, updateDTO, email);

        return ResponseEntity.ok(new ApiResponse<>("Space updated successfully", updatedSpace));
    }

}