package com.cts.customer.dto;

import com.cts.customer.enums.DocumentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDTO {

    private Long documentId;
    private Long applicationId;
    private String documentType;
    private String fileUri;
    private LocalDateTime uploadedDate;
    private DocumentStatus status;
}
