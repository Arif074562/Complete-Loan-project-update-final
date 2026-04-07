package com.cts.collections.service.impl;

import com.cts.collections.dto.DelinquencyRequestDTO;
import com.cts.collections.dto.DelinquencyResponseDTO;
import com.cts.collections.dto.DelinquencyPatchDTO;
import com.cts.collections.entity.DelinquencyRecord;
import com.cts.collections.enums.DelinquencyBucket;
import com.cts.collections.enums.DelinquencyStatus;
import com.cts.collections.exception.ResourceNotFoundException;
import com.cts.collections.mapper.DelinquencyRecordMapper;
import com.cts.collections.repository.DelinquencyRecordRepository;
import com.cts.collections.service.DelinquencyRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DelinquencyRecordServiceImpl implements DelinquencyRecordService {

    private final DelinquencyRecordRepository repository;
    private final DelinquencyRecordMapper mapper;

    @Override
    public DelinquencyResponseDTO create(DelinquencyRequestDTO request) {
        log.info("Creating delinquency record for loan account: {}", request.getLoanAccountId());
        DelinquencyRecord entity = mapper.toEntity(request);
        if (entity.getRecordedDate() == null) {
            entity.setRecordedDate(LocalDate.now());
        }
        entity.setStatus(DelinquencyStatus.ACTIVE);
        entity = repository.save(entity);
        log.info("Delinquency record created: {}", entity.getDelinquencyId());
        return mapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public DelinquencyResponseDTO getById(Long id) {
        log.info("Fetching delinquency record: {}", id);
        DelinquencyRecord entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delinquency record not found with id: " + id));
        return mapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DelinquencyResponseDTO> getAll() {
        log.info("Fetching all delinquency records");
        return mapper.toResponseDTOList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DelinquencyResponseDTO> getByLoanAccountId(Long loanAccountId) {
        log.info("Fetching delinquency records for account: {}", loanAccountId);
        return mapper.toResponseDTOList(repository.findByLoanAccountId(loanAccountId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DelinquencyResponseDTO> getByBucket(DelinquencyBucket bucket) {
        log.info("Fetching delinquency records for bucket: {}", bucket);
        return mapper.toResponseDTOList(repository.findByBucket(bucket));
    }

    @Override
    public DelinquencyResponseDTO patch(Long id, DelinquencyPatchDTO patch) {
        log.info("Patching delinquency record: {}", id);
        DelinquencyRecord entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delinquency record not found with id: " + id));
        if (patch.getDaysPastDue() != null) {
            entity.setDaysPastDue(patch.getDaysPastDue());
        }
        if (patch.getBucket() != null) {
            entity.setBucket(patch.getBucket());
        }
        if (patch.getStatus() != null) {
            entity.setStatus(patch.getStatus());
        }
        entity = repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Override
    public DelinquencyResponseDTO resolve(Long id) {
        log.info("Resolving delinquency record: {}", id);
        DelinquencyRecord entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delinquency record not found with id: " + id));
        entity.setStatus(DelinquencyStatus.RESOLVED);
        entity = repository.save(entity);
        return mapper.toResponseDTO(entity);
    }
}
