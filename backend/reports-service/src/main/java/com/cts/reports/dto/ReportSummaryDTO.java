package com.cts.reports.dto;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportSummaryDTO {

    private long totalReports;
    private Map<String, Object> dashboardMetrics;
}
