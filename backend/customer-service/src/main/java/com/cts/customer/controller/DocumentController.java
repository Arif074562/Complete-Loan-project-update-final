package com.cts.customer.controller;

import com.cts.customer.common.ApiResponse;
import com.cts.customer.dto.DocumentRequestDTO;
import com.cts.customer.dto.DocumentResponseDTO;
import com.cts.customer.enums.DocumentStatus;
import com.cts.customer.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DocumentResponseDTO>> upload(@Valid @RequestBody DocumentRequestDTO request) {
        log.info("POST /api/documents");
        DocumentResponseDTO dto = documentService.upload(request);
        return ResponseEntity.ok(ApiResponse.success("Document uploaded", dto));
    }

    @GetMapping("/application/{appId}")
    public ResponseEntity<ApiResponse<List<DocumentResponseDTO>>> getByApplicationId(@PathVariable Long appId) {
        log.info("GET /api/documents/application/{}", appId);
        List<DocumentResponseDTO> list = documentService.getByApplicationId(appId);
        return ResponseEntity.ok(ApiResponse.success("Documents retrieved", list));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DocumentResponseDTO>> updateStatus(@PathVariable Long id,
                                                                        @RequestBody DocumentStatus status) {
        log.info("PATCH /api/documents/{}/status", id);
        DocumentResponseDTO dto = documentService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Document status updated", dto));
    }
}
