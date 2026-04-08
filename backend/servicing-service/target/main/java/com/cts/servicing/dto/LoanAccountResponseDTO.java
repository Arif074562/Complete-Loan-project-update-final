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

    private Long id;
    private Long applicationId;
    private Long disbursementId;
    private BigDecimal outstandingBalance;
    private LocalDate nextDueDate;
    private BigDecimal emiAmount;
    private String status;
    private String createdAt;
}
