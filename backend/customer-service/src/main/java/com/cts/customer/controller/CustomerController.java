package com.cts.customer.controller;

import com.cts.customer.common.ApiResponse;
import com.cts.customer.dto.CustomerRequestDTO;
import com.cts.customer.dto.CustomerResponseDTO;
import com.cts.customer.enums.KycStatus;
import com.cts.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> create(@Valid @RequestBody CustomerRequestDTO request) {
        log.info("POST /api/customers");
        CustomerResponseDTO dto = customerService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Customer created", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponseDTO>>> list(Pageable pageable) {
        log.info("GET /api/customers");
        Page<CustomerResponseDTO> page = customerService.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success("Customers retrieved", page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/customers/{}", id);
        CustomerResponseDTO dto = customerService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Customer retrieved", dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> update(@PathVariable Long id,
                                                                     @Valid @RequestBody CustomerRequestDTO request) {
        log.info("PUT /api/customers/{}", id);
        CustomerResponseDTO dto = customerService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Customer updated", dto));
    }

    @PatchMapping("/{id}/kyc-status")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateKycStatus(@PathVariable Long id,
                                                                           @RequestBody KycStatus status) {
        log.info("PATCH /api/customers/{}/kyc-status", id);
        CustomerResponseDTO dto = customerService.updateKycStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("KYC status updated", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/customers/{}", id);
        customerService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Customer deleted", null));
    }
}
