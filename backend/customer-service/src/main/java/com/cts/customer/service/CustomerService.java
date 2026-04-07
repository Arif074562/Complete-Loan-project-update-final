package com.cts.customer.service;

import com.cts.customer.dto.CustomerRequestDTO;
import com.cts.customer.dto.CustomerResponseDTO;
import com.cts.customer.enums.KycStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerResponseDTO create(CustomerRequestDTO request);

    Page<CustomerResponseDTO> findAll(Pageable pageable);

    CustomerResponseDTO getById(Long id);

    CustomerResponseDTO update(Long id, CustomerRequestDTO request);

    CustomerResponseDTO updateKycStatus(Long id, KycStatus status);

    void delete(Long id);
}
