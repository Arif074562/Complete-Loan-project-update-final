package com.cts.customer.entity;

import com.cts.customer.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private Long applicationId;
    private String documentType;
    private String fileUri;
    private LocalDateTime uploadedDate;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;
}
