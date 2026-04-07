package com.cts.underwriting.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreResponseDTO {

    private Long scoreId;
    private Long applicationId;
    private Integer scoreValue;
    private String reportRef;
    private LocalDateTime generatedDate;
}
