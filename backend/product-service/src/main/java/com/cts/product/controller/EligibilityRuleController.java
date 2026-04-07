package com.cts.product.controller;

import com.cts.product.common.ApiResponse;
import com.cts.product.dto.EligibilityRuleRequestDTO;
import com.cts.product.dto.EligibilityRuleResponseDTO;
import com.cts.product.service.EligibilityRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility-rules")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EligibilityRuleController {

    private final EligibilityRuleService eligibilityRuleService;

    @PostMapping
    public ResponseEntity<ApiResponse<EligibilityRuleResponseDTO>> create(@Valid @RequestBody EligibilityRuleRequestDTO request) {
        log.info("POST /api/eligibility-rules");
        EligibilityRuleResponseDTO dto = eligibilityRuleService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Eligibility rule created", dto));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<List<EligibilityRuleResponseDTO>>> getByProductId(@PathVariable Long id) {
        log.info("GET /api/eligibility-rules/product/{}", id);
        List<EligibilityRuleResponseDTO> list = eligibilityRuleService.getByProductId(id);
        return ResponseEntity.ok(ApiResponse.success("Eligibility rules retrieved", list));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EligibilityRuleResponseDTO>> update(@PathVariable Long id,
                                                                        @Valid @RequestBody EligibilityRuleRequestDTO request) {
        log.info("PUT /api/eligibility-rules/{}", id);
        EligibilityRuleResponseDTO dto = eligibilityRuleService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Eligibility rule updated", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/eligibility-rules/{}", id);
        eligibilityRuleService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Eligibility rule deleted", null));
    }
}
