package com.cts.disbursement.dto;

import com.cts.disbursement.enums.DisbursementStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementResponseDTO {

    private Long disbursementId;
    private Long applicationId;
    private BigDecimal amount;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private LocalDate disbursementDate;
    private DisbursementStatus status;
}
