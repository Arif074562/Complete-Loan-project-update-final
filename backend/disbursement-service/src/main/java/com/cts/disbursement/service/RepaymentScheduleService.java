package com.cts.disbursement.service;

import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.enums.InstallmentStatus;

import java.util.List;

public interface RepaymentScheduleService {

    List<RepaymentScheduleResponseDTO> getByLoanAccountId(Long loanAccountId);

    RepaymentScheduleResponseDTO updateStatus(Long id, InstallmentStatus status);
}
