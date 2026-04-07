package com.cts.customer.dto;

import com.cts.customer.enums.ApplicationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplicationResponseDTO {

    private Long applicationId;
    private Long customerId;
    private Long productId;
    private BigDecimal requestedAmount;
    private Integer tenureMonths;
    private LocalDate applicationDate;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
