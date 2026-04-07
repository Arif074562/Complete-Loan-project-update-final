package com.cts.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    private String token;
    private Long userId;
    private String name;
    private String email;
    private String role;
    private Long expiresIn;
}
