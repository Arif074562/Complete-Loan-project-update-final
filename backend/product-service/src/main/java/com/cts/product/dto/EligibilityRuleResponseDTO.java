package com.cts.product.dto;

import com.cts.product.enums.ProductStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EligibilityRuleResponseDTO {

    private Long ruleId;
    private Long productId;
    private String ruleExpression;
    private ProductStatus status;
}
