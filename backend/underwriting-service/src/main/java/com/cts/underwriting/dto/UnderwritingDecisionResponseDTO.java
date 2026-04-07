package com.cts.underwriting.dto;

import com.cts.underwriting.enums.DecisionType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnderwritingDecisionResponseDTO {

    private Long decisionId;
    private Long applicationId;
    private Long underwriterId;
    private DecisionType decision;
    private String remarks;
    private LocalDateTime decisionDate;
}
