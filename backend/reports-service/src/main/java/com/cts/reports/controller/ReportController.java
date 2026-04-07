package com.cts.reports.controller;

import com.cts.reports.common.ApiResponse;
import com.cts.reports.dto.GenerateReportRequestDTO;
import com.cts.reports.dto.PortfolioReportResponseDTO;
import com.cts.reports.dto.ReportSummaryDTO;
import com.cts.reports.service.PortfolioReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ReportController {

    private final PortfolioReportService portfolioReportService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<PortfolioReportResponseDTO>> generate(@Valid @RequestBody GenerateReportRequestDTO request) {
        log.info("POST /api/reports/generate");
        PortfolioReportResponseDTO dto = portfolioReportService.generate(request);
        return ResponseEntity.ok(ApiResponse.success("Report generated", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PortfolioReportResponseDTO>>> getAll(Pageable pageable) {
        log.info("GET /api/reports");
        Page<PortfolioReportResponseDTO> page = portfolioReportService.getAll(pageable);
        return ResponseEntity.ok(ApiResponse.success("Reports retrieved", page));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ReportSummaryDTO>> getSummary() {
        log.info("GET /api/reports/summary");
        ReportSummaryDTO dto = portfolioReportService.getSummary();
        return ResponseEntity.ok(ApiResponse.success("Summary retrieved", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PortfolioReportResponseDTO>> getById(@PathVariable Long id) {
        log.info("GET /api/reports/{}", id);
        PortfolioReportResponseDTO dto = portfolioReportService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Report retrieved", dto));
    }
}
