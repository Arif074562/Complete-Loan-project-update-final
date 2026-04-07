package com.cts.reports.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioReportResponseDTO {

    private Long reportId;
    private String scope;
    private Map<String, Object> metrics;
    private LocalDateTime generatedDate;
}
