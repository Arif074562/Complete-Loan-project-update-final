package com.cts.servicing.service;

import com.cts.servicing.dto.RepaymentRequestDTO;
import com.cts.servicing.dto.RepaymentResponseDTO;

import java.util.List;

public interface RepaymentService {

    RepaymentResponseDTO record(RepaymentRequestDTO request);

    List<RepaymentResponseDTO> getByLoanAccountId(Long loanAccountId);

    RepaymentResponseDTO getById(Long id);
}
