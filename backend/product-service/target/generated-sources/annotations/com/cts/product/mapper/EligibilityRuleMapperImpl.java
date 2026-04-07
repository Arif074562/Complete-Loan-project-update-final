package com.cts.product.mapper;

import com.cts.product.dto.EligibilityRuleRequestDTO;
import com.cts.product.dto.EligibilityRuleResponseDTO;
import com.cts.product.entity.EligibilityRule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T15:21:47+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class EligibilityRuleMapperImpl implements EligibilityRuleMapper {

    @Override
    public EligibilityRuleResponseDTO toResponseDTO(EligibilityRule entity) {
        if ( entity == null ) {
            return null;
        }

        EligibilityRuleResponseDTO.EligibilityRuleResponseDTOBuilder eligibilityRuleResponseDTO = EligibilityRuleResponseDTO.builder();

        eligibilityRuleResponseDTO.productId( entity.getProductId() );
        eligibilityRuleResponseDTO.ruleExpression( entity.getRuleExpression() );
        eligibilityRuleResponseDTO.status( entity.getStatus() );

        return eligibilityRuleResponseDTO.build();
    }

    @Override
    public EligibilityRule toEntity(EligibilityRuleRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EligibilityRule.EligibilityRuleBuilder eligibilityRule = EligibilityRule.builder();

        eligibilityRule.productId( dto.getProductId() );
        eligibilityRule.ruleExpression( dto.getRuleExpression() );
        eligibilityRule.status( dto.getStatus() );

        return eligibilityRule.build();
    }

    @Override
    public List<EligibilityRuleResponseDTO> toResponseDTOList(List<EligibilityRule> entities) {
        if ( entities == null ) {
            return null;
        }

        List<EligibilityRuleResponseDTO> list = new ArrayList<EligibilityRuleResponseDTO>( entities.size() );
        for ( EligibilityRule eligibilityRule : entities ) {
            list.add( toResponseDTO( eligibilityRule ) );
        }

        return list;
    }
}
