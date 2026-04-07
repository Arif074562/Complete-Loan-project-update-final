package com.cts.reports.service;

import com.cts.reports.dto.GenerateReportRequestDTO;
import com.cts.reports.dto.PortfolioReportResponseDTO;
import com.cts.reports.dto.ReportSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PortfolioReportService {

    PortfolioReportResponseDTO generate(GenerateReportRequestDTO request);

    Page<PortfolioReportResponseDTO> getAll(Pageable pageable);

    PortfolioReportResponseDTO getById(Long id);

    ReportSummaryDTO getSummary();
}
