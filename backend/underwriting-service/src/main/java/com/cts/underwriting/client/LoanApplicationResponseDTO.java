package com.cts.underwriting.client;

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
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
