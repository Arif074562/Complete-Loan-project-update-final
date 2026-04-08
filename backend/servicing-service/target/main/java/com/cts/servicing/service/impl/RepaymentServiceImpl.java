package com.cts.servicing.service.impl;

import com.cts.servicing.dto.RepaymentRequestDTO;
import com.cts.servicing.dto.RepaymentResponseDTO;
import com.cts.servicing.entity.Repayment;
import com.cts.servicing.enums.RepaymentStatus;
import com.cts.servicing.exception.ResourceNotFoundException;
import com.cts.servicing.mapper.RepaymentMapper;
import com.cts.servicing.repository.RepaymentRepository;
import com.cts.servicing.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;
    private final RepaymentMapper repaymentMapper;

    @Override
    public RepaymentResponseDTO record(RepaymentRequestDTO request) {
        log.info("Recording repayment for loan account: {}", request.getLoanAccountId());
        Repayment entity = repaymentMapper.toEntity(request);
        if (entity.getPaidDate() == null) {
            entity.setPaidDate(LocalDate.now());
        }
        entity.setStatus(RepaymentStatus.SUCCESS);
        entity = repaymentRepository.save(entity);
        log.info("Repayment recorded: {}", entity.getRepaymentId());
        return repaymentMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RepaymentResponseDTO> getByLoanAccountId(Long loanAccountId) {
        log.info("Fetching repayments for loan account: {}", loanAccountId);
        return repaymentRepository.findByLoanAccountId(loanAccountId).stream()
                .map(repaymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RepaymentResponseDTO getById(Long id) {
        log.info("Fetching repayment: {}", id);
        Repayment entity = repaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment not found with id: " + id));
        return repaymentMapper.toResponseDTO(entity);
    }
}
