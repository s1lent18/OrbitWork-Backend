package com.example.orbitwork.controller;

import com.example.orbitwork.dto.ApiResponse;
import com.example.orbitwork.dto.RoomDTO;
import com.example.orbitwork.dto.UpdateRoomDTO;
import com.example.orbitwork.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spaces/{spaceId}/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<RoomDTO>> addRoom(
            @PathVariable Long spaceId,
            @Valid @RequestBody RoomDTO roomDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        RoomDTO room = roomService.addRoom(roomDTO, email, spaceId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Room added successfully", room));
    }

    @PatchMapping("/{roomId}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<RoomDTO>> updateRoom(
            @PathVariable Long spaceId,
            @PathVariable Long roomId,
            @Valid @RequestBody UpdateRoomDTO updateDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();

        RoomDTO updatedRoom =
                roomService.updateRoom(spaceId, roomId, updateDTO, email);

        return ResponseEntity.ok(
                new ApiResponse<>("Room updated successfully", updatedRoom)
        );
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<Page<RoomDTO>> getRooms(
            @PathVariable Long spaceId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(roomService.getRooms(spaceId, pageable));
    }
}