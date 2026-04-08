package com.cts.servicing.mapper;

import com.cts.servicing.dto.LoanAccountRequestDTO;
import com.cts.servicing.dto.LoanAccountResponseDTO;
import com.cts.servicing.entity.LoanAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanAccountMapper {

    @Mapping(source = "loanAccountId", target = "id")
    @Mapping(source = "applicationId", target = "applicationId")
    @Mapping(source = "disbursementId", target = "disbursementId")
    @Mapping(source = "outstandingBalance", target = "outstandingBalance")
    @Mapping(source = "nextDueDate", target = "nextDueDate")
    @Mapping(source = "emiAmount", target = "emiAmount")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    LoanAccountResponseDTO toResponseDTO(LoanAccount entity);

    @Mapping(target = "loanAccountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "applicationId", target = "applicationId")
    @Mapping(source = "disbursementId", target = "disbursementId")
    @Mapping(source = "outstandingBalance", target = "outstandingBalance")
    @Mapping(source = "nextDueDate", target = "nextDueDate")
    @Mapping(source = "emiAmount", target = "emiAmount")
    LoanAccount toEntity(LoanAccountRequestDTO dto);

    List<LoanAccountResponseDTO> toResponseDTOList(List<LoanAccount> entities);

    default LocalDate stringToLocalDate(String date) {
        return date != null ? LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    default String localDateToString(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    default String statusToString(com.cts.servicing.enums.LoanAccountStatus status) {
        return status != null ? status.name() : null;
    }
}
