package com.cts.product.service;

import com.cts.product.dto.EligibilityRuleRequestDTO;
import com.cts.product.dto.EligibilityRuleResponseDTO;

import java.util.List;

public interface EligibilityRuleService {

    EligibilityRuleResponseDTO create(EligibilityRuleRequestDTO request);

    List<EligibilityRuleResponseDTO> getByProductId(Long productId);

    EligibilityRuleResponseDTO update(Long id, EligibilityRuleRequestDTO request);

    void delete(Long id);
}
