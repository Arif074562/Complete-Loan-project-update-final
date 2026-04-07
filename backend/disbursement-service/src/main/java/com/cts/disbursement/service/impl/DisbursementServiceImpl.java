package com.cts.disbursement.service.impl;

import com.cts.disbursement.dto.DisbursementRequestDTO;
import com.cts.disbursement.dto.DisbursementResponseDTO;
import com.cts.disbursement.dto.GenerateScheduleRequestDTO;
import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.entity.Disbursement;
import com.cts.disbursement.entity.RepaymentSchedule;
import com.cts.disbursement.enums.DisbursementStatus;
import com.cts.disbursement.enums.InstallmentStatus;
import com.cts.disbursement.exception.BusinessException;
import com.cts.disbursement.exception.ResourceNotFoundException;
import com.cts.disbursement.mapper.DisbursementMapper;
import com.cts.disbursement.mapper.RepaymentScheduleMapper;
import com.cts.disbursement.repository.DisbursementRepository;
import com.cts.disbursement.repository.RepaymentScheduleRepository;
import com.cts.disbursement.service.DisbursementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DisbursementServiceImpl implements DisbursementService {

    private final DisbursementRepository disbursementRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final DisbursementMapper disbursementMapper;
    private final RepaymentScheduleMapper repaymentScheduleMapper;

    @Override
    public DisbursementResponseDTO create(DisbursementRequestDTO request) {
        log.info("Creating disbursement for application: {}", request.getApplicationId());
        Disbursement entity = disbursementMapper.toEntity(request);
        if (entity.getDisbursementDate() == null) {
            entity.setDisbursementDate(LocalDate.now());
        }
        entity.setStatus(DisbursementStatus.PENDING);
        entity = disbursementRepository.save(entity);
        log.info("Disbursement created: {}", entity.getDisbursementId());
        return disbursementMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public DisbursementResponseDTO getById(Long id) {
        log.info("Fetching disbursement: {}", id);
        Disbursement entity = disbursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement not found with id: " + id));
        return disbursementMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public DisbursementResponseDTO getByApplicationId(Long applicationId) {
        log.info("Fetching disbursement for application: {}", applicationId);
        Disbursement entity = disbursementRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement not found for application: " + applicationId));
        return disbursementMapper.toResponseDTO(entity);
    }

    @Override
    public DisbursementResponseDTO updateStatus(Long id, DisbursementStatus status) {
        log.info("Updating disbursement status: {} to {}", id, status);
        Disbursement entity = disbursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement not found with id: " + id));
        entity.setStatus(status);
        entity = disbursementRepository.save(entity);
        return disbursementMapper.toResponseDTO(entity);
    }

    @Override
    public List<RepaymentScheduleResponseDTO> generateSchedule(Long disbursementId, GenerateScheduleRequestDTO request) {
        log.info("Generating EMI schedule for disbursement: {}", disbursementId);
        Disbursement disbursement = disbursementRepository.findById(disbursementId)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement not found with id: " + disbursementId));

        double principal = disbursement.getAmount().doubleValue();
        double annualRate = request.getAnnualInterestRate().doubleValue();
        int n = request.getTenureMonths();
        LocalDate startDate = disbursement.getDisbursementDate();
        Long loanAccountId = request.getLoanAccountId();

        double r = annualRate / 12.0 / 100.0;
        if (r <= 0 || n <= 0) {
            throw new BusinessException("Invalid rate or tenure");
        }
        double emi = principal * r * Math.pow(1 + r, n) / (Math.pow(1 + r, n) - 1);
        double outstandingBalance = principal;

        List<RepaymentSchedule> schedules = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            double interestComponent = outstandingBalance * r;
            double principalComponent = emi - interestComponent;
            outstandingBalance = outstandingBalance - principalComponent;
            if (i == n) {
                principalComponent += outstandingBalance;
                outstandingBalance = 0;
            }

            RepaymentSchedule schedule = RepaymentSchedule.builder()
                    .loanAccountId(loanAccountId)
                    .installmentNo(i)
                    .dueDate(startDate.plusMonths(i))
                    .principalComponent(BigDecimal.valueOf(principalComponent).setScale(2, RoundingMode.HALF_UP))
                    .interestComponent(BigDecimal.valueOf(interestComponent).setScale(2, RoundingMode.HALF_UP))
                    .totalAmount(BigDecimal.valueOf(emi).setScale(2, RoundingMode.HALF_UP))
                    .status(InstallmentStatus.DUE)
                    .build();
            schedule = repaymentScheduleRepository.save(schedule);
            schedules.add(schedule);
        }

        log.info("Generated {} installments for loan account: {}", schedules.size(), loanAccountId);
        return schedules.stream().map(repaymentScheduleMapper::toResponseDTO).collect(Collectors.toList());
    }
}
