package com.cts.underwriting.service;

import com.cts.underwriting.dto.UnderwritingDecisionRequestDTO;
import com.cts.underwriting.dto.UnderwritingDecisionResponseDTO;

import java.util.List;

public interface UnderwritingDecisionService {

    UnderwritingDecisionResponseDTO record(UnderwritingDecisionRequestDTO request);

    UnderwritingDecisionResponseDTO getById(Long id);

    List<UnderwritingDecisionResponseDTO> getByApplicationId(Long applicationId);

    UnderwritingDecisionResponseDTO update(Long id, UnderwritingDecisionRequestDTO request);
}
