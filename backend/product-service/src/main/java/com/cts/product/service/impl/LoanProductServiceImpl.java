package com.cts.product.service.impl;

import com.cts.product.dto.LoanProductRequestDTO;
import com.cts.product.dto.LoanProductResponseDTO;
import com.cts.product.dto.NotificationRequestDTO;
import com.cts.product.entity.LoanProduct;
import com.cts.product.enums.ProductStatus;
import com.cts.product.exception.ResourceNotFoundException;
import com.cts.product.mapper.LoanProductMapper;
import com.cts.product.repository.LoanProductRepository;
import com.cts.product.service.LoanProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoanProductServiceImpl implements LoanProductService {

    private final LoanProductRepository loanProductRepository;
    private final LoanProductMapper loanProductMapper;
    private final RestTemplate restTemplate;

    @Override
    public LoanProductResponseDTO create(LoanProductRequestDTO request) {
        log.info("Creating loan product");
        LoanProduct entity = loanProductMapper.toEntity(request);
        entity.setStatus(ProductStatus.ACTIVE);
        entity = loanProductRepository.save(entity);
        log.info("Loan product created: {}", entity.getProductId());
        
        sendProductCreatedNotification(entity);
        
        return loanProductMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoanProductResponseDTO> findAll(Pageable pageable) {
        log.info("Fetching loan products page");
        return loanProductRepository.findAll(pageable).map(loanProductMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanProductResponseDTO getById(Long id) {
        log.info("Fetching loan product: {}", id);
        LoanProduct entity = loanProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan product not found with id: " + id));
        return loanProductMapper.toResponseDTO(entity);
    }

    @Override
    public LoanProductResponseDTO update(Long id, LoanProductRequestDTO request) {
        log.info("Updating loan product: {}", id);
        LoanProduct entity = loanProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan product not found with id: " + id));
        entity.setName(request.getName());
        entity.setInterestRate(request.getInterestRate());
        entity.setMinAmount(request.getMinAmount());
        entity.setMaxAmount(request.getMaxAmount());
        entity.setMinTenure(request.getMinTenure());
        entity.setMaxTenure(request.getMaxTenure());
        entity = loanProductRepository.save(entity);
        return loanProductMapper.toResponseDTO(entity);
    }

    @Override
    public void deactivate(Long id) {
        log.info("Deleting loan product: {}", id);
        LoanProduct entity = loanProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan product not found with id: " + id));
        loanProductRepository.delete(entity);
    }
    
    private void sendProductCreatedNotification(LoanProduct product) {
        try {
            NotificationRequestDTO notification = NotificationRequestDTO.builder()
                    .userId(null)
                    .message(String.format("New loan product '%s' is now available! Interest rate: %.2f%%, Amount: ₹%s - ₹%s",
                            product.getName(),
                            product.getInterestRate(),
                            product.getMinAmount(),
                            product.getMaxAmount()))
                    .category("PRODUCT")
                    .status("UNREAD")
                    .build();
            
            restTemplate.postForObject(
                    "http://notification-service/api/notifications/broadcast",
                    notification,
                    String.class
            );
            log.info("Product creation notification sent for product: {}", product.getProductId());
        } catch (Exception e) {
            log.error("Failed to send product creation notification: {}", e.getMessage());
        }
    }
    
    private void sendProductDeactivatedNotification(LoanProduct product) {
        try {
            NotificationRequestDTO notification = NotificationRequestDTO.builder()
                    .userId(null)
                    .message(String.format("Loan product '%s' has been deactivated and is no longer available.",
                            product.getName()))
                    .category("PRODUCT")
                    .status("UNREAD")
                    .build();
            
            restTemplate.postForObject(
                    "http://notification-service/api/notifications/broadcast",
                    notification,
                    String.class
            );
            log.info("Product deactivation notification sent for product: {}", product.getProductId());
        } catch (Exception e) {
            log.error("Failed to send product deactivation notification: {}", e.getMessage());
        }
    }
}
