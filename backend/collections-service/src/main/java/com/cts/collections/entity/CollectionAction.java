package com.cts.collections.entity;

import com.cts.collections.enums.ActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "collection_actions")
public class CollectionAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actionId;

    private Long loanAccountId;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String notes;
    private LocalDate actionDate;
    private String performedBy;
}
