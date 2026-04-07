package com.cts.auth.dto;

import com.cts.auth.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private UserRole role;
    private String phone;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
