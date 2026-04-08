package com.cts.servicing.controller;

import com.cts.servicing.common.ApiResponse;
import com.cts.servicing.dto.RepaymentRequestDTO;
import com.cts.servicing.dto.RepaymentResponseDTO;
import com.cts.servicing.service.RepaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repayments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RepaymentController {

    private final RepaymentService repaymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<RepaymentResponseDTO>> record(@Valid @RequestBody RepaymentRequestDTO request) {
        log.info("POST /api/repayments");
        RepaymentResponseDTO dto = repaymentService.record(request);
        return ResponseEntity.ok(ApiResponse.success("Repayment recorded", dto));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<RepaymentResponseDTO>>> getByLoanAccountId(@PathVariable Long id) {
        log.info("GET /api/repayments/account/{}", id);
        List<RepaymentResponseDTO> list = repaymentService.getByLoanAccountId(id);
        return ResponseEntity.ok(ApiResponse.success("Repayments retrieved", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RepaymentResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/repayments/{}", id);
        RepaymentResponseDTO dto = repaymentService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Repayment retrieved", dto));
    }
}
