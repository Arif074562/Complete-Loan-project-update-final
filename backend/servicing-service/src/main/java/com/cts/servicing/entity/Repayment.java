package com.cts.servicing.entity;

import com.cts.servicing.enums.RepaymentMode;
import com.cts.servicing.enums.RepaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "repayments")
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repaymentId;

    private Long loanAccountId;
    private LocalDate paidDate;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private RepaymentMode mode;

    @Enumerated(EnumType.STRING)
    private RepaymentStatus status;
}
