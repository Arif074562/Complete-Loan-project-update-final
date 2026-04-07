package com.cts.customer.mapper;

import com.cts.customer.dto.CustomerRequestDTO;
import com.cts.customer.dto.CustomerResponseDTO;
import com.cts.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "kycStatus", ignore = true)
    Customer toEntity(CustomerRequestDTO dto);

    CustomerResponseDTO toResponseDTO(Customer entity);

    List<CustomerResponseDTO> toResponseDTOList(List<Customer> entities);
}
