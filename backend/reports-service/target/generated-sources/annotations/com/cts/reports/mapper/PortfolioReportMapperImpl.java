package com.cts.reports.mapper;

import com.cts.reports.dto.PortfolioReportResponseDTO;
import com.cts.reports.entity.PortfolioReport;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T15:21:49+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PortfolioReportMapperImpl implements PortfolioReportMapper {

    @Override
    public PortfolioReportResponseDTO toResponseDTO(PortfolioReport entity) {
        if ( entity == null ) {
            return null;
        }

        PortfolioReportResponseDTO.PortfolioReportResponseDTOBuilder portfolioReportResponseDTO = PortfolioReportResponseDTO.builder();

        portfolioReportResponseDTO.generatedDate( entity.getGeneratedDate() );
        Map<String, Object> map = entity.getMetrics();
        if ( map != null ) {
            portfolioReportResponseDTO.metrics( new LinkedHashMap<String, Object>( map ) );
        }
        portfolioReportResponseDTO.reportId( entity.getReportId() );
        portfolioReportResponseDTO.scope( entity.getScope() );

        return portfolioReportResponseDTO.build();
    }

    @Override
    public List<PortfolioReportResponseDTO> toResponseDTOList(List<PortfolioReport> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PortfolioReportResponseDTO> list = new ArrayList<PortfolioReportResponseDTO>( entities.size() );
        for ( PortfolioReport portfolioReport : entities ) {
            list.add( toResponseDTO( portfolioReport ) );
        }

        return list;
    }
}
