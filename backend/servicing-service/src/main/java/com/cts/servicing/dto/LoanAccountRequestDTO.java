package com.cts.servicing.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanAccountRequestDTO {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Sanctioned amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    private BigDecimal sanctionedAmount;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0", message = "Rate must be >= 0")
    private BigDecimal interestRate;

    @NotNull(message = "Tenure is required")
    @Positive(message = "Tenure must be positive")
    private Integer tenureMonths;

    private LocalDate startDate;
}
