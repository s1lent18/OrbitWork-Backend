package com.example.orbitwork.service;

import com.example.orbitwork.dto.SpaceDTO;

public interface SpaceService {

    SpaceDTO registerSpace(SpaceDTO spaceDTO, Long vendorId);
}
