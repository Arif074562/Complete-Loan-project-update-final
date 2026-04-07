package com.cts.collections.controller;

import com.cts.collections.common.ApiResponse;
import com.cts.collections.dto.CollectionActionRequestDTO;
import com.cts.collections.dto.CollectionActionResponseDTO;
import com.cts.collections.service.CollectionActionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection-actions")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CollectionActionController {

    private final CollectionActionService collectionActionService;

    @PostMapping
    public ResponseEntity<ApiResponse<CollectionActionResponseDTO>> create(@Valid @RequestBody CollectionActionRequestDTO request) {
        log.info("POST /api/collection-actions");
        CollectionActionResponseDTO dto = collectionActionService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Collection action created", dto));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<CollectionActionResponseDTO>>> getByAccountId(@PathVariable Long id) {
        log.info("GET /api/collection-actions/account/{}", id);
        List<CollectionActionResponseDTO> list = collectionActionService.getByLoanAccountId(id);
        return ResponseEntity.ok(ApiResponse.success("Collection actions retrieved", list));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CollectionActionResponseDTO>>> getAll(Pageable pageable) {
        log.info("GET /api/collection-actions");
        Page<CollectionActionResponseDTO> page = collectionActionService.getAll(pageable);
        return ResponseEntity.ok(ApiResponse.success("Collection actions retrieved", page));
    }
}
