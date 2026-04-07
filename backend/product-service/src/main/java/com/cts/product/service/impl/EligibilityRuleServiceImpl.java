package com.cts.product.service.impl;

import com.cts.product.dto.EligibilityRuleRequestDTO;
import com.cts.product.dto.EligibilityRuleResponseDTO;
import com.cts.product.dto.NotificationRequestDTO;
import com.cts.product.entity.EligibilityRule;
import com.cts.product.enums.ProductStatus;
import com.cts.product.exception.ResourceNotFoundException;
import com.cts.product.mapper.EligibilityRuleMapper;
import com.cts.product.repository.EligibilityRuleRepository;
import com.cts.product.service.EligibilityRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EligibilityRuleServiceImpl implements EligibilityRuleService {

    private final EligibilityRuleRepository eligibilityRuleRepository;
    private final EligibilityRuleMapper eligibilityRuleMapper;
    private final RestTemplate restTemplate;

    @Override
    public EligibilityRuleResponseDTO create(EligibilityRuleRequestDTO request) {
        log.info("Creating eligibility rule for product: {}", request.getProductId());
        EligibilityRule entity = eligibilityRuleMapper.toEntity(request);
        if (entity.getStatus() == null) {
            entity.setStatus(ProductStatus.ACTIVE);
        }
        entity = eligibilityRuleRepository.save(entity);
        log.info("Eligibility rule created: {}", entity.getRuleId());
        
        sendRuleCreatedNotification(entity);
        
        return eligibilityRuleMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EligibilityRuleResponseDTO> getByProductId(Long productId) {
        log.info("Fetching eligibility rules for product: {}", productId);
        return eligibilityRuleRepository.findByProductId(productId).stream()
                .map(eligibilityRuleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EligibilityRuleResponseDTO update(Long id, EligibilityRuleRequestDTO request) {
        log.info("Updating eligibility rule: {}", id);
        EligibilityRule entity = eligibilityRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Eligibility rule not found with id: " + id));
        entity.setProductId(request.getProductId());
        entity.setRuleExpression(request.getRuleExpression());
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        entity = eligibilityRuleRepository.save(entity);
        return eligibilityRuleMapper.toResponseDTO(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting eligibility rule: {}", id);
        if (!eligibilityRuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Eligibility rule not found with id: " + id);
        }
        eligibilityRuleRepository.deleteById(id);
    }
    
    private void sendRuleCreatedNotification(EligibilityRule rule) {
        try {
            NotificationRequestDTO notification = NotificationRequestDTO.builder()
                    .userId(null)
                    .message(String.format("New eligibility rule added for product ID %d. Check the updated requirements!",
                            rule.getProductId()))
                    .category("PRODUCT")
                    .status("UNREAD")
                    .build();
            
            restTemplate.postForObject(
                    "http://notification-service/api/notifications/broadcast",
                    notification,
                    String.class
            );
            log.info("Eligibility rule creation notification sent for rule: {}", rule.getRuleId());
        } catch (Exception e) {
            log.error("Failed to send eligibility rule notification: {}", e.getMessage());
        }
    }
}
