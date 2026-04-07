package com.cts.disbursement.controller;

import com.cts.disbursement.common.ApiResponse;
import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.enums.InstallmentStatus;
import com.cts.disbursement.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repayment-schedules")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponseDTO>>> getByLoanAccountId(@PathVariable Long id) {
        log.info("GET /api/repayment-schedules/account/{}", id);
        List<RepaymentScheduleResponseDTO> list = repaymentScheduleService.getByLoanAccountId(id);
        return ResponseEntity.ok(ApiResponse.success("Repayment schedule retrieved", list));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<RepaymentScheduleResponseDTO>> updateStatus(@PathVariable Long id,
                                                                                   @RequestBody InstallmentStatus status) {
        log.info("PATCH /api/repayment-schedules/{}/status", id);
        RepaymentScheduleResponseDTO dto = repaymentScheduleService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Status updated", dto));
    }
}
