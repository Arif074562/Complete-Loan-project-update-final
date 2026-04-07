package com.cts.collections.dto;

import com.cts.collections.enums.ActionType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionActionResponseDTO {

    private Long actionId;
    private Long loanAccountId;
    private ActionType actionType;
    private String notes;
    private LocalDate actionDate;
    private String performedBy;
}
