package com.cts.auth.controller;

import com.cts.auth.common.ApiResponse;
import com.cts.auth.dto.LoginRequestDTO;
import com.cts.auth.dto.LoginResponseDTO;
import com.cts.auth.dto.UserRequestDTO;
import com.cts.auth.dto.UserResponseDTO;
import com.cts.auth.entity.AuditLog;
import com.cts.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody UserRequestDTO request) {
        log.info("POST /api/auth/register");
        UserResponseDTO dto = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        log.info("POST /api/auth/login");
        LoginResponseDTO dto = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", dto));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        log.info("GET /api/auth/users");
        List<UserResponseDTO> list = authService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved", list));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        log.info("GET /api/auth/users/{}", id);
        UserResponseDTO dto = authService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User retrieved", dto));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable Long id,
                                                                   @Valid @RequestBody UserRequestDTO request) {
        log.info("PUT /api/auth/users/{}", id);
        UserResponseDTO dto = authService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success("User updated", dto));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        log.info("DELETE /api/auth/users/{}", id);
        authService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deactivated", null));
    }

    @GetMapping("/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAuditLogs() {
        log.info("GET /api/auth/audit-logs");
        List<AuditLog> logs = authService.getAuditLogs();
        return ResponseEntity.ok(ApiResponse.success("Audit logs retrieved", logs));
    }
}
