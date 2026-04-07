package com.cts.servicing.dto;

import com.cts.servicing.enums.RepaymentMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepaymentRequestDTO {

    @NotNull(message = "Loan account ID is required")
    private Long loanAccountId;

    private LocalDate paidDate;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Mode is required")
    private RepaymentMode mode;
}
