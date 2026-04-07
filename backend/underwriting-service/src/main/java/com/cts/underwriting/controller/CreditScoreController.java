package com.cts.underwriting.controller;

import com.cts.underwriting.common.ApiResponse;
import com.cts.underwriting.dto.CreditScoreRequestDTO;
import com.cts.underwriting.dto.CreditScoreResponseDTO;
import com.cts.underwriting.service.CreditScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-scores")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CreditScoreController {

    private final CreditScoreService creditScoreService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreditScoreResponseDTO>> create(@Valid @RequestBody CreditScoreRequestDTO request) {
        log.info("POST /api/credit-scores");
        CreditScoreResponseDTO dto = creditScoreService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Credit score created", dto));
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<ApiResponse<List<CreditScoreResponseDTO>>> getByApplicationId(@PathVariable Long id) {
        log.info("GET /api/credit-scores/application/{}", id);
        List<CreditScoreResponseDTO> list = creditScoreService.getByApplicationId(id);
        return ResponseEntity.ok(ApiResponse.success("Credit scores retrieved", list));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CreditScoreResponseDTO>> update(@PathVariable Long id,
                                                                       @Valid @RequestBody CreditScoreRequestDTO request) {
        log.info("PUT /api/credit-scores/{}", id);
        CreditScoreResponseDTO dto = creditScoreService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Credit score updated", dto));
    }
}
