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

    @NotNull(message = "Disbursement ID is required")
    private Long disbursementId;

    @NotNull(message = "Outstanding balance is required")
    @DecimalMin(value = "0", message = "Balance must be >= 0")
    private BigDecimal outstandingBalance;

    @NotNull(message = "Next due date is required")
    private String nextDueDate;

    @NotNull(message = "EMI amount is required")
    @DecimalMin(value = "0", message = "EMI must be >= 0")
    private BigDecimal emiAmount;
}
