package com.example.orbitwork.service.impl;

import com.example.orbitwork.dto.RoomDTO;
import com.example.orbitwork.dto.UpdateRoomDTO;
import com.example.orbitwork.entity.Room;
import com.example.orbitwork.entity.Space;
import com.example.orbitwork.exception.RoomNotFoundException;
import com.example.orbitwork.exception.SpaceNotFoundException;
import com.example.orbitwork.repository.RoomRepository;
import com.example.orbitwork.repository.SpaceRepository;
import com.example.orbitwork.service.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final SpaceRepository spaceRepository;

    public RoomServiceImpl(RoomRepository roomRepository, SpaceRepository spaceRepository) {
        this.roomRepository = roomRepository;
        this.spaceRepository = spaceRepository;
    }

    private RoomDTO mapToDto(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getPrice()
        );
    }

    @Override
    public RoomDTO addRoom(RoomDTO roomDTO, String vendorEmail, Long spaceId) {

        Space space = spaceRepository.findByIdAndVendor_Email(spaceId, vendorEmail)
                .orElseThrow(() -> new SpaceNotFoundException("Space not Found or You don't own this space"));

        Room room = new Room(
                roomDTO.getName(),
                roomDTO.getCapacity(),
                roomDTO.getPrice(),
                LocalDateTime.now()
        );

        space.addRoom(room);

        spaceRepository.save(space);

        return mapToDto(room);
    }

    @Transactional
    @Override
    public RoomDTO updateRoom(
            Long spaceId,
            Long roomId,
            UpdateRoomDTO updateDTO,
            String vendorEmail
    ) {

        Room room = roomRepository
                .findByIdAndSpace_IdAndSpace_Vendor_Email(
                        roomId, spaceId, vendorEmail
                )
                .orElseThrow(() ->
                        new RoomNotFoundException("Room not found or you don't own it")
                );

        if (updateDTO.getName() != null) {
            room.setName(updateDTO.getName());
        }

        if (updateDTO.getCapacity() != null) {
            room.setCapacity(updateDTO.getCapacity());
        }

        if (updateDTO.getPrice() != null) {
            room.setPrice(updateDTO.getPrice());
        }

        return mapToDto(room);
    }

    @Override
    public Page<RoomDTO> getRooms(Long spaceId, Pageable pageable) {

        Page<Room> rooms = roomRepository.findBySpace_Id(spaceId, pageable);

        return rooms.map(this::mapToDto);
    }
}
