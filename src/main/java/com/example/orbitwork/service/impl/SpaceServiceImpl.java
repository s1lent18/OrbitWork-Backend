package com.example.orbitwork.service.impl;

import com.example.orbitwork.dto.SpaceDTO;
import com.example.orbitwork.dto.UpdateSpaceDTO;
import com.example.orbitwork.entity.Space;
import com.example.orbitwork.entity.User;
import com.example.orbitwork.exception.LocationNotFoundException;
import com.example.orbitwork.exception.SpaceNotFoundException;
import com.example.orbitwork.exception.UserNotFoundException;
import com.example.orbitwork.repository.SpaceRepository;
import com.example.orbitwork.repository.UserRepository;
import com.example.orbitwork.service.SpaceService;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public SpaceDTO updateSpace(Long spaceId, UpdateSpaceDTO updateDTO, String vendorEmail) {

        Space space = spaceRepository.findByIdAndVendor_Email(spaceId, vendorEmail)
                .orElseThrow(() -> new SpaceNotFoundException("Space not found or you do not own this space"));

        if (updateDTO.getName() != null) {
            space.setName(updateDTO.getName());
        }

        if (updateDTO.getDescription() != null) {
            space.setDescription(updateDTO.getDescription());
        }

        if (updateDTO.getGoogleMapLocation() != null) {
            space.setGoogleMapLocation(updateDTO.getGoogleMapLocation());
        }

        if (updateDTO.getCity() != null) {
            space.setCity(updateDTO.getCity());
        }

        if (updateDTO.getPrice() != null) {
            space.setPrice(updateDTO.getPrice());
        }

        if (updateDTO.getOpenTime() != null) {
            space.setOpenTime(updateDTO.getOpenTime());
        }

        if (updateDTO.getCloseTime() != null) {
            space.setOpenTime(updateDTO.getCloseTime());
        }

        Space updatedSpace = spaceRepository.save(space);

        return mapToDto(updatedSpace);
    }

}