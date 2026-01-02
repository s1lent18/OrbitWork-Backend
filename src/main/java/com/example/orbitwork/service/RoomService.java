package com.example.orbitwork.service;

import com.example.orbitwork.dto.RoomDTO;
import com.example.orbitwork.dto.UpdateRoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {

    RoomDTO addRoom(RoomDTO roomDTO, String vendorEmail, Long spaceId);

    RoomDTO updateRoom(Long spaceId, Long roomId, UpdateRoomDTO updateDTO, String vendorEmail);

    Page<RoomDTO> getRooms(Long spaceId, Pageable pageable);
}
