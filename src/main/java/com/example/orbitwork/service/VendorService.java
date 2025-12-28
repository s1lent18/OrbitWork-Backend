package com.example.orbitwork.service;

import com.example.orbitwork.dto.LoginResponse;
import com.example.orbitwork.dto.VendorDTO;
import org.springframework.stereotype.Service;

@Service
public interface VendorService {

    LoginResponse register(VendorDTO vendorDTO);

    LoginResponse getVendor(String email);
}