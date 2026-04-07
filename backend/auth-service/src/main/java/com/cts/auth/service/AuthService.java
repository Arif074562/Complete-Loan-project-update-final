package com.cts.auth.service;

import com.cts.auth.dto.LoginRequestDTO;
import com.cts.auth.dto.LoginResponseDTO;
import com.cts.auth.dto.UserRequestDTO;
import com.cts.auth.dto.UserResponseDTO;

import java.util.List;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

    UserResponseDTO register(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO request);

    void deactivateUser(Long id);

    List<com.cts.auth.entity.AuditLog> getAuditLogs();
}
