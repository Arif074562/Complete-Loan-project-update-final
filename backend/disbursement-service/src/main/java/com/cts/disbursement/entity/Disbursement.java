package com.cts.disbursement.entity;

import com.cts.disbursement.enums.DisbursementStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "disbursements")
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disbursementId;

    private Long applicationId;
    private BigDecimal amount;
    private LocalDate disbursementDate;

    @Enumerated(EnumType.STRING)
    private DisbursementStatus status;
}
