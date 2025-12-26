package com.example.orbitwork.service;

import com.example.orbitwork.dto.LoginResponse;
import com.example.orbitwork.dto.UserDTO;

public interface UserService {

    LoginResponse register(UserDTO userDTO);

}