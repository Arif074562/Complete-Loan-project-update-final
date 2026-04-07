package com.cts.reports.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateReportRequestDTO {

    @NotBlank(message = "Scope is required")
    private String scope;
}
