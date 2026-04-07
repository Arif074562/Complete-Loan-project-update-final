package com.cts.product.entity;

import com.cts.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "eligibility_rules")
public class EligibilityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    private Long productId;
    private String ruleExpression;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
