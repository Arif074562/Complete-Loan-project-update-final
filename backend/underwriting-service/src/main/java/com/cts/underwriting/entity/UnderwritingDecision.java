package com.cts.underwriting.entity;

import com.cts.underwriting.enums.DecisionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "underwriting_decisions")
public class UnderwritingDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long decisionId;

    private Long applicationId;
    private Long underwriterId;

    @Enumerated(EnumType.STRING)
    private DecisionType decision;

    private String remarks;
    private LocalDateTime decisionDate;
}
