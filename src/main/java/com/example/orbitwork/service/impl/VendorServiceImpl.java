package com.example.orbitwork.service.impl;

import com.example.orbitwork.dto.LoginResponse;
import com.example.orbitwork.dto.VendorDTO;
import com.example.orbitwork.entity.Role;
import com.example.orbitwork.entity.User;
import com.example.orbitwork.exception.UserAlreadyExistsException;
import com.example.orbitwork.exception.UserNotFoundException;
import com.example.orbitwork.repository.RoleRepository;
import com.example.orbitwork.repository.UserRepository;
import com.example.orbitwork.service.VendorService;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class VendorServiceImpl implements VendorService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public VendorServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public LoginResponse register(VendorDTO vendorDTO) {

        if (userRepository.findByEmail(vendorDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("vendor already exists with this email");
        }

        Role userRole = roleRepository.findByName("VENDOR")
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Default role VENDOR not found. Please seed roles table."));

        User user = new User(
                vendorDTO.getName(),
                vendorDTO.getEmail(),
                passwordEncoder.encode(vendorDTO.getPassword())
        );

        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        return new LoginResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    @Override
    public LoginResponse getVendor(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Vendor Not Found"));

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}