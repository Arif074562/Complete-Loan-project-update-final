package com.cts.servicing.service;

import com.cts.servicing.dto.LoanAccountRequestDTO;
import com.cts.servicing.dto.LoanAccountResponseDTO;
import com.cts.servicing.enums.LoanAccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanAccountService {

    LoanAccountResponseDTO create(LoanAccountRequestDTO request);

    Page<LoanAccountResponseDTO> findAll(Pageable pageable);

    LoanAccountResponseDTO getById(Long id);

    LoanAccountResponseDTO updateStatus(Long id, LoanAccountStatus status);
}
