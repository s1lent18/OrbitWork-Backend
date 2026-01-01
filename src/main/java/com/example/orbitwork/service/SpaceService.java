package com.example.orbitwork.service;

import com.example.orbitwork.dto.SpaceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SpaceService {

    SpaceDTO registerSpace(SpaceDTO spaceDTO, String vendorEmail);

    Page<SpaceDTO> getSpaces(Pageable pageable, String location);

    Page<SpaceDTO> getVendorSpaces(Pageable pageable, Long vendorId);
}
