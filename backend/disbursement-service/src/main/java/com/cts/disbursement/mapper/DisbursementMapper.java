package com.cts.disbursement.mapper;

import com.cts.disbursement.dto.DisbursementRequestDTO;
import com.cts.disbursement.dto.DisbursementResponseDTO;
import com.cts.disbursement.entity.Disbursement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisbursementMapper {

    @Mapping(source = "disbursementId", target = "disbursementId")
    @Mapping(source = "applicationId", target = "applicationId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "ifscCode", target = "ifscCode")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "disbursementDate", target = "disbursementDate")
    @Mapping(source = "status", target = "status")
    DisbursementResponseDTO toResponseDTO(Disbursement entity);

    @Mapping(target = "disbursementId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "applicationId", target = "applicationId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "ifscCode", target = "ifscCode")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "disbursementDate", target = "disbursementDate")
    Disbursement toEntity(DisbursementRequestDTO dto);

    List<DisbursementResponseDTO> toResponseDTOList(List<Disbursement> entities);
}
