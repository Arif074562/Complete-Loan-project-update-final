package com.cts.collections.dto;

import com.cts.collections.enums.DelinquencyBucket;
import com.cts.collections.enums.DelinquencyStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DelinquencyResponseDTO {

    private Long delinquencyId;
    private Long loanAccountId;
    private Integer daysPastDue;
    private DelinquencyBucket bucket;
    private DelinquencyStatus status;
    private LocalDate recordedDate;
}
