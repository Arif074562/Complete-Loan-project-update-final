package com.cts.reports.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "servicing-service", url = "${service.servicing.url}")
public interface ServicingServiceClient {

    @GetMapping("/api/loan-accounts")
    Object getLoanAccounts();
}
