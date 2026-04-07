package com.cts.collections.service;

import com.cts.collections.dto.DelinquencyRequestDTO;
import com.cts.collections.dto.DelinquencyResponseDTO;
import com.cts.collections.dto.DelinquencyPatchDTO;
import com.cts.collections.enums.DelinquencyBucket;

import java.util.List;

public interface DelinquencyRecordService {

    DelinquencyResponseDTO create(DelinquencyRequestDTO request);

    DelinquencyResponseDTO getById(Long id);

    List<DelinquencyResponseDTO> getAll();

    List<DelinquencyResponseDTO> getByLoanAccountId(Long loanAccountId);

    List<DelinquencyResponseDTO> getByBucket(DelinquencyBucket bucket);

    DelinquencyResponseDTO patch(Long id, DelinquencyPatchDTO patch);

    DelinquencyResponseDTO resolve(Long id);
}
