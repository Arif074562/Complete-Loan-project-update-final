package com.cts.customer.service;

import com.cts.customer.dto.DocumentRequestDTO;
import com.cts.customer.dto.DocumentResponseDTO;
import com.cts.customer.enums.DocumentStatus;

import java.util.List;

public interface DocumentService {

    DocumentResponseDTO upload(DocumentRequestDTO request);

    List<DocumentResponseDTO> getByApplicationId(Long applicationId);

    DocumentResponseDTO updateStatus(Long id, DocumentStatus status);
}
