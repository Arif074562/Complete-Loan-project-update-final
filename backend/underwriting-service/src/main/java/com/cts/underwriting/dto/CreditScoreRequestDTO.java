package com.cts.underwriting.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreRequestDTO {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Score value is required")
    @Min(value = 0, message = "Score must be >= 0")
    @Max(value = 900, message = "Score must be <= 900")
    private Integer scoreValue;

    private String reportRef;
}
