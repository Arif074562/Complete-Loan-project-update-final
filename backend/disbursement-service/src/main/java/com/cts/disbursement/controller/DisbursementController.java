package com.cts.disbursement.controller;

import com.cts.disbursement.common.ApiResponse;
import com.cts.disbursement.dto.DisbursementRequestDTO;
import com.cts.disbursement.dto.DisbursementResponseDTO;
import com.cts.disbursement.dto.GenerateScheduleRequestDTO;
import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.enums.DisbursementStatus;
import com.cts.disbursement.service.DisbursementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disbursements")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DisbursementController {

    private final DisbursementService disbursementService;

    @PostMapping
    public ResponseEntity<ApiResponse<DisbursementResponseDTO>> create(@Valid @RequestBody DisbursementRequestDTO request) {
        log.info("POST /api/disbursements");
        DisbursementResponseDTO dto = disbursementService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Disbursement created", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DisbursementResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/disbursements/{}", id);
        DisbursementResponseDTO dto = disbursementService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Disbursement retrieved", dto));
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<ApiResponse<DisbursementResponseDTO>> getByApplicationId(@PathVariable Long id) {
        log.info("GET /api/disbursements/application/{}", id);
        DisbursementResponseDTO dto = disbursementService.getByApplicationId(id);
        return ResponseEntity.ok(ApiResponse.success("Disbursement retrieved", dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DisbursementResponseDTO>> updateStatus(@PathVariable Long id,
                                                                              @RequestBody DisbursementStatus status) {
        log.info("PATCH /api/disbursements/{}/status", id);
        DisbursementResponseDTO dto = disbursementService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Status updated", dto));
    }

    @PostMapping("/{id}/generate-schedule")
    public ResponseEntity<ApiResponse<List<RepaymentScheduleResponseDTO>>> generateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody GenerateScheduleRequestDTO request) {
        log.info("POST /api/disbursements/{}/generate-schedule", id);
        List<RepaymentScheduleResponseDTO> list = disbursementService.generateSchedule(id, request);
        return ResponseEntity.ok(ApiResponse.success("Schedule generated", list));
    }
}
