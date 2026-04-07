package com.cts.collections.controller;

import com.cts.collections.common.ApiResponse;
import com.cts.collections.dto.DelinquencyRequestDTO;
import com.cts.collections.dto.DelinquencyResponseDTO;
import com.cts.collections.dto.DelinquencyPatchDTO;
import com.cts.collections.enums.DelinquencyBucket;
import com.cts.collections.service.DelinquencyRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delinquency")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DelinquencyController {

    private final DelinquencyRecordService delinquencyRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<DelinquencyResponseDTO>> create(@Valid @RequestBody DelinquencyRequestDTO request) {
        log.info("POST /api/delinquency");
        DelinquencyResponseDTO dto = delinquencyRecordService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Delinquency record created", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DelinquencyResponseDTO>>> getAll() {
        log.info("GET /api/delinquency");
        List<DelinquencyResponseDTO> list = delinquencyRecordService.getAll();
        return ResponseEntity.ok(ApiResponse.success("Delinquency records retrieved", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DelinquencyResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/delinquency/{}", id);
        DelinquencyResponseDTO dto = delinquencyRecordService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Delinquency record retrieved", dto));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<DelinquencyResponseDTO>>> getByAccountId(@PathVariable Long id) {
        log.info("GET /api/delinquency/account/{}", id);
        List<DelinquencyResponseDTO> list = delinquencyRecordService.getByLoanAccountId(id);
        return ResponseEntity.ok(ApiResponse.success("Delinquency records retrieved", list));
    }

    @GetMapping("/bucket/{bucket}")
    public ResponseEntity<ApiResponse<List<DelinquencyResponseDTO>>> getByBucket(@PathVariable DelinquencyBucket bucket) {
        log.info("GET /api/delinquency/bucket/{}", bucket);
        List<DelinquencyResponseDTO> list = delinquencyRecordService.getByBucket(bucket);
        return ResponseEntity.ok(ApiResponse.success("Delinquency records retrieved", list));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DelinquencyResponseDTO>> patch(@PathVariable Long id,
                                                                       @RequestBody DelinquencyPatchDTO patch) {
        log.info("PATCH /api/delinquency/{}", id);
        DelinquencyResponseDTO dto = delinquencyRecordService.patch(id, patch);
        return ResponseEntity.ok(ApiResponse.success("Delinquency record updated", dto));
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<ApiResponse<DelinquencyResponseDTO>> resolve(@PathVariable Long id) {
        log.info("PATCH /api/delinquency/{}/resolve", id);
        DelinquencyResponseDTO dto = delinquencyRecordService.resolve(id);
        return ResponseEntity.ok(ApiResponse.success("Delinquency record resolved", dto));
    }
}
