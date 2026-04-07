package com.cts.servicing.mapper;

import com.cts.servicing.dto.LoanAccountRequestDTO;
import com.cts.servicing.dto.LoanAccountResponseDTO;
import com.cts.servicing.entity.LoanAccount;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T15:21:51+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class LoanAccountMapperImpl implements LoanAccountMapper {

    @Override
    public LoanAccountResponseDTO toResponseDTO(LoanAccount entity) {
        if ( entity == null ) {
            return null;
        }

        LoanAccountResponseDTO.LoanAccountResponseDTOBuilder loanAccountResponseDTO = LoanAccountResponseDTO.builder();

        loanAccountResponseDTO.applicationId( entity.getApplicationId() );
        loanAccountResponseDTO.interestRate( entity.getInterestRate() );
        loanAccountResponseDTO.sanctionedAmount( entity.getSanctionedAmount() );
        loanAccountResponseDTO.startDate( entity.getStartDate() );
        loanAccountResponseDTO.tenureMonths( entity.getTenureMonths() );

        return loanAccountResponseDTO.build();
    }

    @Override
    public LoanAccount toEntity(LoanAccountRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LoanAccount.LoanAccountBuilder loanAccount = LoanAccount.builder();

        loanAccount.applicationId( dto.getApplicationId() );
        loanAccount.interestRate( dto.getInterestRate() );
        loanAccount.sanctionedAmount( dto.getSanctionedAmount() );
        loanAccount.startDate( dto.getStartDate() );
        loanAccount.tenureMonths( dto.getTenureMonths() );

        return loanAccount.build();
    }

    @Override
    public List<LoanAccountResponseDTO> toResponseDTOList(List<LoanAccount> entities) {
        if ( entities == null ) {
            return null;
        }

        List<LoanAccountResponseDTO> list = new ArrayList<LoanAccountResponseDTO>( entities.size() );
        for ( LoanAccount loanAccount : entities ) {
            list.add( toResponseDTO( loanAccount ) );
        }

        return list;
    }
}
