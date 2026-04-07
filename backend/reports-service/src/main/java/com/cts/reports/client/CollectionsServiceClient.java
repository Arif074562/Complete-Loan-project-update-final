package com.cts.reports.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "collections-service", url = "${service.collections.url}")
public interface CollectionsServiceClient {

    @GetMapping("/api/delinquency")
    Object getDelinquency();
}
