package com.cts.customer.service;

import com.cts.customer.dto.LoanApplicationRequestDTO;
import com.cts.customer.dto.LoanApplicationResponseDTO;
import com.cts.customer.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanApplicationService {

    LoanApplicationResponseDTO submit(LoanApplicationRequestDTO request);

    Page<LoanApplicationResponseDTO> findAll(Pageable pageable);

    LoanApplicationResponseDTO getById(Long id);

    LoanApplicationResponseDTO update(Long id, LoanApplicationRequestDTO request);

    LoanApplicationResponseDTO updateStatus(Long id, ApplicationStatus status);

    List<LoanApplicationResponseDTO> getByCustomerId(Long customerId);
}
