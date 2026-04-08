package com.cts.servicing.service.impl;

import com.cts.servicing.dto.LoanAccountRequestDTO;
import com.cts.servicing.dto.LoanAccountResponseDTO;
import com.cts.servicing.dto.NotificationRequestDTO;
import com.cts.servicing.entity.LoanAccount;
import com.cts.servicing.enums.LoanAccountStatus;
import com.cts.servicing.exception.ResourceNotFoundException;
import com.cts.servicing.mapper.LoanAccountMapper;
import com.cts.servicing.repository.LoanAccountRepository;
import com.cts.servicing.service.LoanAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoanAccountServiceImpl implements LoanAccountService {

    private final LoanAccountRepository loanAccountRepository;
    private final LoanAccountMapper loanAccountMapper;
    private final RestTemplate restTemplate;

    @Override
    public LoanAccountResponseDTO create(LoanAccountRequestDTO request) {
        log.info("Creating loan account for application: {}", request.getApplicationId());
        LoanAccount entity = loanAccountMapper.toEntity(request);
        entity.setStatus(LoanAccountStatus.ACTIVE);
        entity = loanAccountRepository.save(entity);
        log.info("Loan account created: {}", entity.getLoanAccountId());
        
        // Send notification (fetch customer ID from application)
        sendAccountCreatedNotification(entity);
        
        return loanAccountMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoanAccountResponseDTO> findAll(Pageable pageable) {
        return loanAccountRepository.findAll(pageable).map(loanAccountMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanAccountResponseDTO getById(Long id) {
        LoanAccount entity = loanAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan account not found with id: " + id));
        return loanAccountMapper.toResponseDTO(entity);
    }

    @Override
    public LoanAccountResponseDTO updateStatus(Long id, LoanAccountStatus status) {
        LoanAccount entity = loanAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan account not found with id: " + id));
        LoanAccountStatus oldStatus = entity.getStatus();
        entity.setStatus(status);
        entity = loanAccountRepository.save(entity);
        
        // Send notification for status change
        sendStatusChangeNotification(entity, oldStatus, status);
        
        return loanAccountMapper.toResponseDTO(entity);
    }
    
    private void sendAccountCreatedNotification(LoanAccount account) {
        try {
            // Fetch customer ID from application service
            Long customerId = fetchCustomerIdFromApplication(account.getApplicationId());
            if (customerId == null) {
                log.warn("Could not fetch customer ID for application: {}", account.getApplicationId());
                return;
            }
            
            NotificationRequestDTO notification = NotificationRequestDTO.builder()
                    .userId(customerId)
                    .message(String.format("Your loan account #%d has been created successfully! Outstanding Balance: ₹%.2f", 
                            account.getLoanAccountId(), account.getOutstandingBalance()))
                    .category("LOAN_ACCOUNT")
                    .status("UNREAD")
                    .build();
            
            restTemplate.postForObject(
                    "http://notification-service/api/notifications",
                    notification,
                    Object.class
            );
            log.info("Notification sent for loan account creation: {}", account.getLoanAccountId());
        } catch (Exception e) {
            log.error("Failed to send notification for loan account creation: {}", e.getMessage());
            // Don't fail the transaction if notification fails
        }
    }
    
    private void sendStatusChangeNotification(LoanAccount account, LoanAccountStatus oldStatus, LoanAccountStatus newStatus) {
        try {
            // Fetch customer ID from application service
            Long customerId = fetchCustomerIdFromApplication(account.getApplicationId());
            if (customerId == null) {
                log.warn("Could not fetch customer ID for application: {}", account.getApplicationId());
                return;
            }
            
            String message = String.format("Your loan account #%d status changed from %s to %s", 
                    account.getLoanAccountId(), oldStatus, newStatus);
            
            NotificationRequestDTO notification = NotificationRequestDTO.builder()
                    .userId(customerId)
                    .message(message)
                    .category("LOAN_ACCOUNT")
                    .status("UNREAD")
                    .build();
            
            restTemplate.postForObject(
                    "http://notification-service/api/notifications",
                    notification,
                    Object.class
            );
            log.info("Notification sent for loan account status change: {}", account.getLoanAccountId());
        } catch (Exception e) {
            log.error("Failed to send notification for status change: {}", e.getMessage());
            // Don't fail the transaction if notification fails
        }
    }
    
    private Long fetchCustomerIdFromApplication(Long applicationId) {
        try {
            // Call customer-service to get application details
            String url = "http://customer-service/api/loan-applications/" + applicationId;
            var response = restTemplate.getForObject(url, java.util.Map.class);
            if (response != null && response.get("data") != null) {
                var data = (java.util.Map<String, Object>) response.get("data");
                Object customerIdObj = data.get("customerId");
                if (customerIdObj instanceof Number) {
                    return ((Number) customerIdObj).longValue();
                }
            }
        } catch (Exception e) {
            log.error("Failed to fetch customer ID from application {}: {}", applicationId, e.getMessage());
        }
        return null;
    }
}
