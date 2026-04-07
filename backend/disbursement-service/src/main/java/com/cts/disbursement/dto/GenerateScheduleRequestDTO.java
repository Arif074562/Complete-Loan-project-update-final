package com.cts.disbursement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateScheduleRequestDTO {

    @NotNull(message = "Loan account ID is required")
    private Long loanAccountId;

    @NotNull(message = "Annual interest rate is required")
    @DecimalMin(value = "0", message = "Rate must be >= 0")
    private BigDecimal annualInterestRate;

    @NotNull(message = "Tenure months is required")
    @Positive(message = "Tenure must be positive")
    private Integer tenureMonths;
}
