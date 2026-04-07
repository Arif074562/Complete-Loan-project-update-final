package com.cts.underwriting.service.impl;

import com.cts.underwriting.dto.CreditScoreRequestDTO;
import com.cts.underwriting.dto.CreditScoreResponseDTO;
import com.cts.underwriting.entity.CreditScore;
import com.cts.underwriting.exception.ResourceNotFoundException;
import com.cts.underwriting.mapper.CreditScoreMapper;
import com.cts.underwriting.repository.CreditScoreRepository;
import com.cts.underwriting.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreditScoreServiceImpl implements CreditScoreService {

    private final CreditScoreRepository creditScoreRepository;
    private final CreditScoreMapper creditScoreMapper;

    @Override
    public CreditScoreResponseDTO create(CreditScoreRequestDTO request) {
        log.info("Creating credit score for application: {}", request.getApplicationId());
        CreditScore entity = creditScoreMapper.toEntity(request);
        entity.setGeneratedDate(LocalDateTime.now());
        entity = creditScoreRepository.save(entity);
        log.info("Credit score created: {}", entity.getScoreId());
        return creditScoreMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditScoreResponseDTO> getByApplicationId(Long applicationId) {
        log.info("Fetching credit scores for application: {}", applicationId);
        return creditScoreRepository.findByApplicationId(applicationId).stream()
                .map(creditScoreMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CreditScoreResponseDTO update(Long id, CreditScoreRequestDTO request) {
        log.info("Updating credit score: {}", id);
        CreditScore entity = creditScoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit score not found with id: " + id));
        entity.setApplicationId(request.getApplicationId());
        entity.setScoreValue(request.getScoreValue());
        entity.setReportRef(request.getReportRef());
        entity = creditScoreRepository.save(entity);
        return creditScoreMapper.toResponseDTO(entity);
    }
}
