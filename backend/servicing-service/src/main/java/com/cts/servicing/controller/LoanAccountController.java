package com.cts.servicing.controller;

import com.cts.servicing.common.ApiResponse;
import com.cts.servicing.dto.LoanAccountRequestDTO;
import com.cts.servicing.dto.LoanAccountResponseDTO;
import com.cts.servicing.enums.LoanAccountStatus;
import com.cts.servicing.service.LoanAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-accounts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class LoanAccountController {

    private final LoanAccountService loanAccountService;

    @PostMapping
    public ResponseEntity<ApiResponse<LoanAccountResponseDTO>> create(@Valid @RequestBody LoanAccountRequestDTO request) {
        log.info("POST /api/loan-accounts");
        LoanAccountResponseDTO dto = loanAccountService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Loan account created", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<LoanAccountResponseDTO>>> list(Pageable pageable) {
        log.info("GET /api/loan-accounts");
        Page<LoanAccountResponseDTO> page = loanAccountService.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success("Loan accounts retrieved", page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanAccountResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/loan-accounts/{}", id);
        LoanAccountResponseDTO dto = loanAccountService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Loan account retrieved", dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<LoanAccountResponseDTO>> updateStatus(@PathVariable Long id,
                                                                            @RequestBody LoanAccountStatus status) {
        log.info("PATCH /api/loan-accounts/{}/status", id);
        LoanAccountResponseDTO dto = loanAccountService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Status updated", dto));
    }
}
