package com.cts.underwriting.dto;

import com.cts.underwriting.enums.DecisionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnderwritingDecisionRequestDTO {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    private Long underwriterId;

    @NotNull(message = "Decision is required")
    private DecisionType decision;

    private String remarks;
}
