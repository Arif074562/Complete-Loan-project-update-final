package com.cts.customer.service.impl;

import com.cts.customer.dto.LoanApplicationRequestDTO;
import com.cts.customer.dto.LoanApplicationResponseDTO;
import com.cts.customer.entity.LoanApplication;
import com.cts.customer.enums.ApplicationStatus;
import com.cts.customer.exception.ResourceNotFoundException;
import com.cts.customer.mapper.LoanApplicationMapper;
import com.cts.customer.repository.LoanApplicationRepository;
import com.cts.customer.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationMapper loanApplicationMapper;

    @Override
    public LoanApplicationResponseDTO submit(LoanApplicationRequestDTO request) {
        log.info("Submitting loan application for customer: {}", request.getCustomerId());
        LoanApplication entity = loanApplicationMapper.toEntity(request);
        entity.setApplicationDate(LocalDate.now());
        entity.setStatus(ApplicationStatus.PENDING);
        entity = loanApplicationRepository.save(entity);
        log.info("Loan application submitted: {}", entity.getApplicationId());
        return loanApplicationMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoanApplicationResponseDTO> findAll(Pageable pageable) {
        log.info("Fetching loan applications page");
        return loanApplicationRepository.findAll(pageable).map(loanApplicationMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanApplicationResponseDTO getById(Long id) {
        log.info("Fetching loan application: {}", id);
        LoanApplication entity = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id: " + id));
        return loanApplicationMapper.toResponseDTO(entity);
    }

    @Override
    public LoanApplicationResponseDTO update(Long id, LoanApplicationRequestDTO request) {
        log.info("Updating loan application: {}", id);
        LoanApplication entity = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id: " + id));
        entity.setCustomerId(request.getCustomerId());
        entity.setProductId(request.getProductId());
        entity.setRequestedAmount(request.getRequestedAmount());
        entity.setTenureMonths(request.getTenureMonths());
        entity = loanApplicationRepository.save(entity);
        return loanApplicationMapper.toResponseDTO(entity);
    }

    @Override
    public LoanApplicationResponseDTO updateStatus(Long id, ApplicationStatus status) {
        log.info("Updating loan application status: {} to {}", id, status);
        LoanApplication entity = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id: " + id));
        entity.setStatus(status);
        entity = loanApplicationRepository.saveAndFlush(entity);
        log.info("Status updated successfully for application: {} to {}", id, entity.getStatus());
        return loanApplicationMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanApplicationResponseDTO> getByCustomerId(Long customerId) {
        log.info("Fetching loan applications for customer: {}", customerId);
        return loanApplicationRepository.findByCustomerId(customerId).stream()
                .map(loanApplicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
