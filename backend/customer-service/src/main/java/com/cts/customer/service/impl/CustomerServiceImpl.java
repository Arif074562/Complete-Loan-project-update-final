package com.cts.customer.service.impl;

import com.cts.customer.dto.CustomerRequestDTO;
import com.cts.customer.dto.CustomerResponseDTO;
import com.cts.customer.entity.Customer;
import com.cts.customer.enums.CustomerStatus;
import com.cts.customer.enums.KycStatus;
import com.cts.customer.exception.ResourceNotFoundException;
import com.cts.customer.mapper.CustomerMapper;
import com.cts.customer.repository.CustomerRepository;
import com.cts.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final com.cts.customer.repository.LoanApplicationRepository loanApplicationRepository;

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO request) {
        log.info("Creating customer");
        Customer entity = customerMapper.toEntity(request);
        entity.setKycStatus(KycStatus.PENDING);
        entity.setStatus(CustomerStatus.ACTIVE);
        entity = customerRepository.save(entity);
        log.info("Customer created: {}", entity.getCustomerId());
        return customerMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> findAll(Pageable pageable) {
        log.info("Fetching customers page");
        return customerRepository.findAll(pageable).map(customerMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getById(Long id) {
        log.info("Fetching customer: {}", id);
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toResponseDTO(entity);
    }

    @Override
    public CustomerResponseDTO update(Long id, CustomerRequestDTO request) {
        log.info("Updating customer: {}", id);
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setAddress(request.getAddress());
        entity.setCity(request.getCity());
        entity.setState(request.getState());
        entity.setPinCode(request.getPinCode());
        entity.setPanNumber(request.getPanNumber());
        entity.setAadharNumber(request.getAadharNumber());
        entity.setSegment(request.getSegment());
        entity = customerRepository.save(entity);
        return customerMapper.toResponseDTO(entity);
    }

    @Override
    public CustomerResponseDTO updateKycStatus(Long id, KycStatus status) {
        log.info("Updating KYC status for customer: {} to {}", id, status);
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        entity.setKycStatus(status);
        entity = customerRepository.save(entity);
        return customerMapper.toResponseDTO(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting customer: {}", id);
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        // Delete related loan applications first to avoid FK constraint
        List<com.cts.customer.entity.LoanApplication> apps = loanApplicationRepository.findByCustomerId(id);
        if (!apps.isEmpty()) {
            loanApplicationRepository.deleteAll(apps);
        }
        customerRepository.deleteById(id);
    }
}
