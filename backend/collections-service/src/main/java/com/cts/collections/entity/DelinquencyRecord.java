package com.cts.collections.entity;

import com.cts.collections.enums.DelinquencyBucket;
import com.cts.collections.enums.DelinquencyStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "delinquency_records")
public class DelinquencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long delinquencyId;

    private Long loanAccountId;
    private Integer daysPastDue;

    @Enumerated(EnumType.STRING)
    private DelinquencyBucket bucket;

    @Enumerated(EnumType.STRING)
    private DelinquencyStatus status;

    private LocalDate recordedDate;
}
