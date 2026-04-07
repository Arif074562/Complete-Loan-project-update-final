package com.cts.auth.controller;

import com.cts.auth.common.ApiResponse;
import com.cts.auth.dto.UserResponseDTO;
import com.cts.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthService authService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        log.info("GET /api/users/all - Inter-service call");
        List<UserResponseDTO> list = authService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved", list));
    }
}
