package com.cts.product.controller;

import com.cts.product.common.ApiResponse;
import com.cts.product.dto.LoanProductRequestDTO;
import com.cts.product.dto.LoanProductResponseDTO;
import com.cts.product.service.LoanProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@Validated
public class LoanProductController {

    private final LoanProductService loanProductService;

    @PostMapping
    public ResponseEntity<ApiResponse<LoanProductResponseDTO>> create(@Valid @RequestBody LoanProductRequestDTO request) {
        log.info("POST /api/products");
        LoanProductResponseDTO dto = loanProductService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Product created", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<LoanProductResponseDTO>>> list(Pageable pageable) {
        log.info("GET /api/products");
        Page<LoanProductResponseDTO> page = loanProductService.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success("Products retrieved", page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanProductResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/products/{}", id);
        LoanProductResponseDTO dto = loanProductService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Product retrieved", dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanProductResponseDTO>> update(@PathVariable Long id,
                                                                     @Valid @RequestBody LoanProductRequestDTO request) {
        log.info("PUT /api/products/{}", id);
        LoanProductResponseDTO dto = loanProductService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Product updated", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable Long id) {
        log.info("DELETE /api/products/{}", id);
        loanProductService.deactivate(id);
        return ResponseEntity.ok(ApiResponse.success("Product deactivated", null));
    }
}
