package com.cts.customer.service.impl;

import com.cts.customer.dto.DocumentRequestDTO;
import com.cts.customer.dto.DocumentResponseDTO;
import com.cts.customer.entity.Document;
import com.cts.customer.enums.DocumentStatus;
import com.cts.customer.exception.ResourceNotFoundException;
import com.cts.customer.mapper.DocumentMapper;
import com.cts.customer.repository.DocumentRepository;
import com.cts.customer.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public DocumentResponseDTO upload(DocumentRequestDTO request) {
        log.info("Uploading document for application: {}", request.getApplicationId());
        Document entity = documentMapper.toEntity(request);
        entity.setUploadedDate(LocalDateTime.now());
        entity.setStatus(DocumentStatus.PENDING);
        entity = documentRepository.save(entity);
        log.info("Document uploaded: {}", entity.getDocumentId());
        return documentMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> getByApplicationId(Long applicationId) {
        log.info("Fetching documents for application: {}", applicationId);
        return documentRepository.findByApplicationId(applicationId).stream()
                .map(documentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentResponseDTO updateStatus(Long id, DocumentStatus status) {
        log.info("Updating document status: {} to {}", id, status);
        Document entity = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        entity.setStatus(status);
        entity = documentRepository.save(entity);
        return documentMapper.toResponseDTO(entity);
    }
}
