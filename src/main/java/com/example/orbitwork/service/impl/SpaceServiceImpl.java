package com.example.orbitwork.service.impl;

import com.example.orbitwork.dto.SpaceDTO;
import com.example.orbitwork.entity.Space;
import com.example.orbitwork.entity.User;
import com.example.orbitwork.exception.LocationNotFoundException;
import com.example.orbitwork.exception.UserNotFoundException;
import com.example.orbitwork.repository.SpaceRepository;
import com.example.orbitwork.repository.UserRepository;
import com.example.orbitwork.service.SpaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public class SpaceServiceImpl implements SpaceService {

    private final UserRepository userRepository;

    private final SpaceRepository spaceRepository;

    public SpaceServiceImpl(UserRepository userRepository, SpaceRepository spaceRepository) {
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
    }

    private SpaceDTO mapToDto(Space space) {
        return new SpaceDTO(
                space.getId(),
                space.getName(),
                space.getDescription(),
                space.getGoogleMapLocation(),
                space.getCity(),
                space.getVendor().getId(),
                space.getVendor().getName(),
                space.getPrice(),
                space.getOpenTime(),
                space.getCloseTime()
        );
    }

    @Override
    public SpaceDTO registerSpace(SpaceDTO spaceDTO, String vendorEmail) {

        User vendor = userRepository.findByEmail(vendorEmail).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (spaceDTO.getOpenTime().isAfter(spaceDTO.getCloseTime())) {
            throw new IllegalArgumentException("Open time must be before close time");
        }

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
                saved.getVendor().getId(),
                saved.getVendor().getName(),
                saved.getPrice(),
                saved.getOpenTime(),
                saved.getCloseTime()
        );
    }

    @Override
    public Page<SpaceDTO> getSpaces(Pageable pageable, String location) {

        if (location.isBlank()) throw new LocationNotFoundException("Location Not Found");

        Page<Space> spacePage = spaceRepository.findByCity(location, pageable);

        return spacePage.map(this::mapToDto);
    }

    @Override
    public Page<SpaceDTO> getVendorSpaces(Pageable pageable, Long vendorId) {

        User vendor = userRepository.findById(vendorId).orElseThrow(() -> new UserNotFoundException("Vendor Not Found"));

        Page<Space> spacePage = spaceRepository.findByVendor_Id(vendor.getId(), pageable);

        return spacePage.map(this::mapToDto);
    }
}