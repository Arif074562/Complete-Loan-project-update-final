package com.cts.underwriting.mapper;

import com.cts.underwriting.dto.UnderwritingDecisionRequestDTO;
import com.cts.underwriting.dto.UnderwritingDecisionResponseDTO;
import com.cts.underwriting.entity.UnderwritingDecision;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:26:53+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UnderwritingDecisionMapperImpl implements UnderwritingDecisionMapper {

    @Override
    public UnderwritingDecisionResponseDTO toResponseDTO(UnderwritingDecision entity) {
        if ( entity == null ) {
            return null;
        }

        UnderwritingDecisionResponseDTO.UnderwritingDecisionResponseDTOBuilder underwritingDecisionResponseDTO = UnderwritingDecisionResponseDTO.builder();

        underwritingDecisionResponseDTO.applicationId( entity.getApplicationId() );
        underwritingDecisionResponseDTO.decision( entity.getDecision() );
        underwritingDecisionResponseDTO.decisionDate( entity.getDecisionDate() );
        underwritingDecisionResponseDTO.decisionId( entity.getDecisionId() );
        underwritingDecisionResponseDTO.remarks( entity.getRemarks() );
        underwritingDecisionResponseDTO.underwriterId( entity.getUnderwriterId() );

        return underwritingDecisionResponseDTO.build();
    }

    @Override
    public UnderwritingDecision toEntity(UnderwritingDecisionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UnderwritingDecision.UnderwritingDecisionBuilder underwritingDecision = UnderwritingDecision.builder();

        underwritingDecision.applicationId( dto.getApplicationId() );
        underwritingDecision.decision( dto.getDecision() );
        underwritingDecision.remarks( dto.getRemarks() );
        underwritingDecision.underwriterId( dto.getUnderwriterId() );

        return underwritingDecision.build();
    }

    @Override
    public List<UnderwritingDecisionResponseDTO> toResponseDTOList(List<UnderwritingDecision> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UnderwritingDecisionResponseDTO> list = new ArrayList<UnderwritingDecisionResponseDTO>( entities.size() );
        for ( UnderwritingDecision underwritingDecision : entities ) {
            list.add( toResponseDTO( underwritingDecision ) );
        }

        return list;
    }
}
