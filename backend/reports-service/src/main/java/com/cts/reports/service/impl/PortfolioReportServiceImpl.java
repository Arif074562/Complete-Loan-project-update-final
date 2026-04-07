package com.cts.reports.service.impl;

import com.cts.reports.client.CollectionsServiceClient;
import com.cts.reports.client.DisbursementServiceClient;
import com.cts.reports.client.ServicingServiceClient;
import com.cts.reports.dto.GenerateReportRequestDTO;
import com.cts.reports.dto.PortfolioReportResponseDTO;
import com.cts.reports.dto.ReportSummaryDTO;
import com.cts.reports.entity.PortfolioReport;
import com.cts.reports.exception.ResourceNotFoundException;
import com.cts.reports.mapper.PortfolioReportMapper;
import com.cts.reports.repository.PortfolioReportRepository;
import com.cts.reports.service.PortfolioReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PortfolioReportServiceImpl implements PortfolioReportService {

    private final PortfolioReportRepository repository;
    private final PortfolioReportMapper mapper;
    private final DisbursementServiceClient disbursementServiceClient;
    private final ServicingServiceClient servicingServiceClient;
    private final CollectionsServiceClient collectionsServiceClient;

    @Override
    public PortfolioReportResponseDTO generate(GenerateReportRequestDTO request) {
        log.info("Generating portfolio report for scope: {}", request.getScope());
        Map<String, Object> metrics = new HashMap<>();
        try {
            Object disbursements = disbursementServiceClient.getDisbursements();
            metrics.put("disbursements", disbursements);
        } catch (Exception e) {
            log.warn("Failed to fetch disbursements: {}", e.getMessage());
            metrics.put("disbursementsError", e.getMessage());
        }
        try {
            Object loanAccounts = servicingServiceClient.getLoanAccounts();
            metrics.put("loanAccounts", loanAccounts);
        } catch (Exception e) {
            log.warn("Failed to fetch loan accounts: {}", e.getMessage());
            metrics.put("loanAccountsError", e.getMessage());
        }
        try {
            Object delinquency = collectionsServiceClient.getDelinquency();
            metrics.put("delinquency", delinquency);
        } catch (Exception e) {
            log.warn("Failed to fetch delinquency: {}", e.getMessage());
            metrics.put("delinquencyError", e.getMessage());
        }
        metrics.put("scope", request.getScope());
        metrics.put("generatedAt", LocalDateTime.now().toString());

        PortfolioReport entity = PortfolioReport.builder()
                .scope(request.getScope())
                .metrics(metrics)
                .generatedDate(LocalDateTime.now())
                .build();
        entity = repository.save(entity);
        log.info("Report generated: {}", entity.getReportId());
        return mapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PortfolioReportResponseDTO> getAll(Pageable pageable) {
        log.info("Fetching all reports (pageable)");
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PortfolioReportResponseDTO getById(Long id) {
        log.info("Fetching report: {}", id);
        PortfolioReport entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));
        return mapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportSummaryDTO getSummary() {
        log.info("Fetching report summary");
        long totalReports = repository.count();
        Map<String, Object> dashboardMetrics = new HashMap<>();
        dashboardMetrics.put("totalReports", totalReports);
        return ReportSummaryDTO.builder()
                .totalReports(totalReports)
                .dashboardMetrics(dashboardMetrics)
                .build();
    }
}
