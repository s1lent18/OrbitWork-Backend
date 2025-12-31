package com.example.orbitwork.service.impl;

import com.example.orbitwork.dto.SpaceDTO;
import com.example.orbitwork.entity.Space;
import com.example.orbitwork.entity.User;
import com.example.orbitwork.exception.UserNotFoundException;
import com.example.orbitwork.repository.SpaceRepository;
import com.example.orbitwork.repository.UserRepository;
import com.example.orbitwork.service.SpaceService;

import java.time.LocalDateTime;

public class SpaceServiceImpl implements SpaceService {

    private final UserRepository userRepository;

    private final SpaceRepository spaceRepository;

    public SpaceServiceImpl(UserRepository userRepository, SpaceRepository spaceRepository) {
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
    }

    @Override
    public SpaceDTO registerSpace(SpaceDTO spaceDTO, String vendorEmail) {

        User vendor = userRepository.findByEmail(vendorEmail).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Space space = new Space(
                spaceDTO.getName(),
                spaceDTO.getDescription(),
                spaceDTO.getGoogleMapLocation(),
                spaceDTO.getCity(),
                vendor,
                spaceDTO.getPrice(),
                LocalDateTime.now(),
                spaceDTO.getOpenTime(),
                spaceDTO.getCloseTime()
        );

        Space saved = spaceRepository.save(space);

        return new SpaceDTO(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getGoogleMapLocation(),
                saved.getCity(),
                saved.getVendor(),
                saved.getPrice(),
                saved.getOpenTime(),
                saved.getCloseTime()
        );
    }
}