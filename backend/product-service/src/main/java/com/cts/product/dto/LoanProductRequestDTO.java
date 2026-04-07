package com.cts.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanProductRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0", message = "Interest rate must be >= 0")
    private BigDecimal interestRate;

    @NotNull(message = "Min amount is required")
    @DecimalMin(value = "0", message = "Min amount must be >= 0")
    private BigDecimal minAmount;

    @NotNull(message = "Max amount is required")
    @DecimalMin(value = "0", message = "Max amount must be >= 0")
    private BigDecimal maxAmount;

    @NotNull(message = "Min tenure is required")
    @Positive(message = "Min tenure must be positive")
    private Integer minTenure;

    @NotNull(message = "Max tenure is required")
    @Positive(message = "Max tenure must be positive")
    private Integer maxTenure;
}
