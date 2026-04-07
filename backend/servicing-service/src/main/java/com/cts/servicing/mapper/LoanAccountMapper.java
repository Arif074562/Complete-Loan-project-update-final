package com.cts.servicing.mapper;

import com.cts.servicing.dto.LoanAccountRequestDTO;
import com.cts.servicing.dto.LoanAccountResponseDTO;
import com.cts.servicing.entity.LoanAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanAccountMapper {

    @Mapping(target = "loanAccountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    LoanAccountResponseDTO toResponseDTO(LoanAccount entity);

    @Mapping(target = "loanAccountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    LoanAccount toEntity(LoanAccountRequestDTO dto);

    List<LoanAccountResponseDTO> toResponseDTOList(List<LoanAccount> entities);
}
