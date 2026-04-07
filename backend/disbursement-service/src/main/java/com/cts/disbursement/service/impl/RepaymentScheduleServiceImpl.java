package com.cts.disbursement.service.impl;

import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.entity.RepaymentSchedule;
import com.cts.disbursement.enums.InstallmentStatus;
import com.cts.disbursement.exception.ResourceNotFoundException;
import com.cts.disbursement.mapper.RepaymentScheduleMapper;
import com.cts.disbursement.repository.RepaymentScheduleRepository;
import com.cts.disbursement.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RepaymentScheduleServiceImpl implements RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final RepaymentScheduleMapper repaymentScheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponseDTO> getByLoanAccountId(Long loanAccountId) {
        log.info("Fetching repayment schedule for loan account: {}", loanAccountId);
        return repaymentScheduleRepository.findByLoanAccountIdOrderByInstallmentNo(loanAccountId).stream()
                .map(repaymentScheduleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepaymentScheduleResponseDTO updateStatus(Long id, InstallmentStatus status) {
        log.info("Updating schedule status: {} to {}", id, status);
        RepaymentSchedule entity = repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment schedule not found with id: " + id));
        entity.setStatus(status);
        entity = repaymentScheduleRepository.save(entity);
        return repaymentScheduleMapper.toResponseDTO(entity);
    }
}
