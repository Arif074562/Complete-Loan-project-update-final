package com.cts.reports.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "disbursement-service", url = "${service.disbursement.url}")
public interface DisbursementServiceClient {

    @GetMapping("/api/disbursements")
    Object getDisbursements();
}
