package com.cts.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.cts.customer.enums.CustomerSegment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String panNumber;
    private String aadharNumber;

    @NotNull(message = "Segment is required")
    private CustomerSegment segment;
}
