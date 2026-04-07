package com.cts.collections.dto;

import com.cts.collections.enums.DelinquencyBucket;
import com.cts.collections.enums.DelinquencyStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DelinquencyPatchDTO {

    private Integer daysPastDue;
    private DelinquencyBucket bucket;
    private DelinquencyStatus status;
}
