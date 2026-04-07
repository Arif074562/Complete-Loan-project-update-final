package com.cts.underwriting.service;

import com.cts.underwriting.dto.CreditScoreRequestDTO;
import com.cts.underwriting.dto.CreditScoreResponseDTO;

import java.util.List;

public interface CreditScoreService {

    CreditScoreResponseDTO create(CreditScoreRequestDTO request);

    List<CreditScoreResponseDTO> getByApplicationId(Long applicationId);

    CreditScoreResponseDTO update(Long id, CreditScoreRequestDTO request);
}
