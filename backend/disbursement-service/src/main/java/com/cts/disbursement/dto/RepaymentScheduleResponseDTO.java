package com.cts.disbursement.dto;

import com.cts.disbursement.enums.InstallmentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepaymentScheduleResponseDTO {

    private Long scheduleId;
    private Long loanAccountId;
    private Integer installmentNo;
    private LocalDate dueDate;
    private BigDecimal principalComponent;
    private BigDecimal interestComponent;
    private BigDecimal totalAmount;
    private InstallmentStatus status;
}
