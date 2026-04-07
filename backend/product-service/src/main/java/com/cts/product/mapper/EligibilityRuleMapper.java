package com.cts.product.mapper;

import com.cts.product.dto.EligibilityRuleRequestDTO;
import com.cts.product.dto.EligibilityRuleResponseDTO;
import com.cts.product.entity.EligibilityRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EligibilityRuleMapper {

    @Mapping(target = "ruleId", ignore = true)
    EligibilityRuleResponseDTO toResponseDTO(EligibilityRule entity);

    @Mapping(target = "ruleId", ignore = true)
    EligibilityRule toEntity(EligibilityRuleRequestDTO dto);

    List<EligibilityRuleResponseDTO> toResponseDTOList(List<EligibilityRule> entities);
}
