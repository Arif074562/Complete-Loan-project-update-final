package com.cts.customer.mapper;

import com.cts.customer.dto.LoanApplicationRequestDTO;
import com.cts.customer.dto.LoanApplicationResponseDTO;
import com.cts.customer.entity.LoanApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {

    @Mapping(source = "applicationId", target = "applicationId")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "requestedAmount", target = "requestedAmount")
    @Mapping(source = "tenureMonths", target = "tenureMonths")
    @Mapping(source = "applicationDate", target = "applicationDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    LoanApplicationResponseDTO toResponseDTO(LoanApplication entity);

    @Mapping(target = "applicationId", ignore = true)
    @Mapping(target = "applicationDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    LoanApplication toEntity(LoanApplicationRequestDTO dto);

    List<LoanApplicationResponseDTO> toResponseDTOList(List<LoanApplication> entities);
}
