package com.cts.servicing.dto;

import com.cts.servicing.enums.LoanAccountStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanAccountResponseDTO {

    private Long loanAccountId;
    private Long applicationId;
    private BigDecimal sanctionedAmount;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private LocalDate startDate;
    private LoanAccountStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
