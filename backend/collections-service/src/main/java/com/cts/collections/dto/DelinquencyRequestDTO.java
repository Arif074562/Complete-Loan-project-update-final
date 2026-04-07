package com.cts.collections.dto;

import com.cts.collections.enums.DelinquencyBucket;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DelinquencyRequestDTO {

    @NotNull(message = "Loan account ID is required")
    private Long loanAccountId;

    @NotNull(message = "Days past due is required")
    private Integer daysPastDue;

    @NotNull(message = "Bucket is required")
    private DelinquencyBucket bucket;

    private LocalDate recordedDate;
}
