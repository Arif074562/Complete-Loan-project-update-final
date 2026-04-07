package com.cts.collections.dto;

import com.cts.collections.enums.ActionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionActionRequestDTO {

    @NotNull(message = "Loan account ID is required")
    private Long loanAccountId;

    @NotNull(message = "Action type is required")
    private ActionType actionType;

    private String notes;
    private LocalDate actionDate;
    private String performedBy;
}
