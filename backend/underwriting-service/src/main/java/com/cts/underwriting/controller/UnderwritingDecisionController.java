package com.cts.underwriting.controller;

import com.cts.underwriting.common.ApiResponse;
import com.cts.underwriting.dto.UnderwritingDecisionRequestDTO;
import com.cts.underwriting.dto.UnderwritingDecisionResponseDTO;
import com.cts.underwriting.service.UnderwritingDecisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/underwriting/decisions")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UnderwritingDecisionController {

    private final UnderwritingDecisionService underwritingDecisionService;

    @PostMapping
    public ResponseEntity<ApiResponse<UnderwritingDecisionResponseDTO>> record(@Valid @RequestBody UnderwritingDecisionRequestDTO request) {
        log.info("POST /api/underwriting/decisions");
        UnderwritingDecisionResponseDTO dto = underwritingDecisionService.record(request);
        return ResponseEntity.ok(ApiResponse.success("Decision recorded", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UnderwritingDecisionResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/underwriting/decisions/{}", id);
        UnderwritingDecisionResponseDTO dto = underwritingDecisionService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Decision retrieved", dto));
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<ApiResponse<List<UnderwritingDecisionResponseDTO>>> getByApplicationId(@PathVariable Long id) {
        log.info("GET /api/underwriting/decisions/application/{}", id);
        List<UnderwritingDecisionResponseDTO> list = underwritingDecisionService.getByApplicationId(id);
        return ResponseEntity.ok(ApiResponse.success("Decisions retrieved", list));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UnderwritingDecisionResponseDTO>> update(@PathVariable Long id,
                                                                               @Valid @RequestBody UnderwritingDecisionRequestDTO request) {
        log.info("PUT /api/underwriting/decisions/{}", id);
        UnderwritingDecisionResponseDTO dto = underwritingDecisionService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Decision updated", dto));
    }
}
