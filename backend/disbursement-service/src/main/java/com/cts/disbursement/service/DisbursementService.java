package com.cts.disbursement.service;

import com.cts.disbursement.dto.DisbursementRequestDTO;
import com.cts.disbursement.dto.DisbursementResponseDTO;
import com.cts.disbursement.dto.GenerateScheduleRequestDTO;
import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.enums.DisbursementStatus;

import java.util.List;

public interface DisbursementService {

    DisbursementResponseDTO create(DisbursementRequestDTO request);

    DisbursementResponseDTO getById(Long id);

    DisbursementResponseDTO getByApplicationId(Long applicationId);

    DisbursementResponseDTO updateStatus(Long id, DisbursementStatus status);

    List<RepaymentScheduleResponseDTO> generateSchedule(Long disbursementId, GenerateScheduleRequestDTO request);
}
