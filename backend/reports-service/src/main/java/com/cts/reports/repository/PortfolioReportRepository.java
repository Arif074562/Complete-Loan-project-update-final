package com.cts.reports.repository;

import com.cts.reports.entity.PortfolioReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioReportRepository extends JpaRepository<PortfolioReport, Long> {
}
