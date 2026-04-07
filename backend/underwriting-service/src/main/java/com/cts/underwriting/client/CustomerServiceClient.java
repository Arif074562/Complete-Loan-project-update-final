package com.cts.underwriting.client;

import com.cts.underwriting.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/api/loan-applications/{id}")
    ApiResponse<LoanApplicationResponseDTO> getApplication(@PathVariable("id") Long id);
}
