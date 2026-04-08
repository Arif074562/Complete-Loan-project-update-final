package com.cts.servicing.dto;

import com.cts.servicing.enums.RepaymentMode;
import com.cts.servicing.enums.RepaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepaymentResponseDTO {

    private Long repaymentId;
    private Long loanAccountId;
    private LocalDate paidDate;
    private BigDecimal amount;
    private RepaymentMode mode;
    private RepaymentStatus status;
}
