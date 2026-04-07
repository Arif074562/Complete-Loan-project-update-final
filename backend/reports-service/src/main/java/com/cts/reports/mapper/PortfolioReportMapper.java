package com.cts.reports.mapper;

import com.cts.reports.dto.PortfolioReportResponseDTO;
import com.cts.reports.entity.PortfolioReport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PortfolioReportMapper {

    PortfolioReportResponseDTO toResponseDTO(PortfolioReport entity);

    List<PortfolioReportResponseDTO> toResponseDTOList(List<PortfolioReport> entities);
}
