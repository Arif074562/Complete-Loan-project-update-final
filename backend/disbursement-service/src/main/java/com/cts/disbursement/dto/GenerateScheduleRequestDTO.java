package com.cts.disbursement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateScheduleRequestDTO {

    @NotNull(message = "Start date is required")
    private String startDate;
}
