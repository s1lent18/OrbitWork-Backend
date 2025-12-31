package com.example.orbitwork.service;

import com.example.orbitwork.dto.SpaceDTO;
import org.springframework.stereotype.Service;

@Service
public interface SpaceService {

    SpaceDTO registerSpace(SpaceDTO spaceDTO, String vendorEmail);
}
