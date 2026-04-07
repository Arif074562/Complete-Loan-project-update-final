package com.cts.product.service;

import com.cts.product.dto.LoanProductRequestDTO;
import com.cts.product.dto.LoanProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanProductService {

    LoanProductResponseDTO create(LoanProductRequestDTO request);

    Page<LoanProductResponseDTO> findAll(Pageable pageable);

    LoanProductResponseDTO getById(Long id);

    LoanProductResponseDTO update(Long id, LoanProductRequestDTO request);

    void deactivate(Long id);
}
