package com.cts.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.cts.customer.enums.CustomerSegment;
import com.cts.customer.enums.CustomerStatus;
import com.cts.customer.enums.KycStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String panNumber;
    private String aadharNumber;
    private KycStatus kycStatus;
    private CustomerSegment segment;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
