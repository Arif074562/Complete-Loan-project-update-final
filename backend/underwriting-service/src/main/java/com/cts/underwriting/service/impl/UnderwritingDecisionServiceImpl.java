package com.cts.underwriting.service.impl;

import com.cts.underwriting.dto.UnderwritingDecisionRequestDTO;
import com.cts.underwriting.dto.UnderwritingDecisionResponseDTO;
import com.cts.underwriting.entity.UnderwritingDecision;
import com.cts.underwriting.exception.ResourceNotFoundException;
import com.cts.underwriting.mapper.UnderwritingDecisionMapper;
import com.cts.underwriting.repository.UnderwritingDecisionRepository;
import com.cts.underwriting.service.UnderwritingDecisionService;
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
public class UnderwritingDecisionServiceImpl implements UnderwritingDecisionService {

    private final UnderwritingDecisionRepository underwritingDecisionRepository;
    private final UnderwritingDecisionMapper underwritingDecisionMapper;

    @Override
    public UnderwritingDecisionResponseDTO record(UnderwritingDecisionRequestDTO request) {
        log.info("Recording underwriting decision for application: {}", request.getApplicationId());
        UnderwritingDecision entity = underwritingDecisionMapper.toEntity(request);
        entity.setDecisionDate(LocalDateTime.now());
        entity = underwritingDecisionRepository.save(entity);
        log.info("Underwriting decision recorded: {}", entity.getDecisionId());
        return underwritingDecisionMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UnderwritingDecisionResponseDTO getById(Long id) {
        log.info("Fetching underwriting decision: {}", id);
        UnderwritingDecision entity = underwritingDecisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Underwriting decision not found with id: " + id));
        return underwritingDecisionMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnderwritingDecisionResponseDTO> getByApplicationId(Long applicationId) {
        log.info("Fetching underwriting decisions for application: {}", applicationId);
        return underwritingDecisionRepository.findByApplicationId(applicationId).stream()
                .map(underwritingDecisionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UnderwritingDecisionResponseDTO update(Long id, UnderwritingDecisionRequestDTO request) {
        log.info("Updating underwriting decision: {}", id);
        UnderwritingDecision entity = underwritingDecisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Underwriting decision not found with id: " + id));
        entity.setApplicationId(request.getApplicationId());
        entity.setUnderwriterId(request.getUnderwriterId());
        entity.setDecision(request.getDecision());
        entity.setRemarks(request.getRemarks());
        entity = underwritingDecisionRepository.save(entity);
        return underwritingDecisionMapper.toResponseDTO(entity);
    }
}
