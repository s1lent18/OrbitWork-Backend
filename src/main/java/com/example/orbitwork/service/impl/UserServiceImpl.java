package com.example.orbitwork.service.impl;

import com.example.orbitwork.dto.LoginResponse;
import com.example.orbitwork.dto.UserDTO;
import com.example.orbitwork.entity.Role;
import com.example.orbitwork.entity.User;
import com.example.orbitwork.exception.UserAlreadyExistsException;
import com.example.orbitwork.repository.RoleRepository;
import com.example.orbitwork.repository.UserRepository;
import com.example.orbitwork.service.UserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse register(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Default role USER not found. Please seed roles table."));

        User user = new User(
               userDTO.getName(),
               userDTO.getEmail(),
               passwordEncoder.encode(userDTO.getPassword())
        );

        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        return new LoginResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                "token"
        );
    }
}