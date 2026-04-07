package com.cts.servicing.mapper;

import com.cts.servicing.dto.RepaymentRequestDTO;
import com.cts.servicing.dto.RepaymentResponseDTO;
import com.cts.servicing.entity.Repayment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:26:50+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class RepaymentMapperImpl implements RepaymentMapper {

    @Override
    public RepaymentResponseDTO toResponseDTO(Repayment entity) {
        if ( entity == null ) {
            return null;
        }

        RepaymentResponseDTO.RepaymentResponseDTOBuilder repaymentResponseDTO = RepaymentResponseDTO.builder();

        repaymentResponseDTO.amount( entity.getAmount() );
        repaymentResponseDTO.loanAccountId( entity.getLoanAccountId() );
        repaymentResponseDTO.mode( entity.getMode() );
        repaymentResponseDTO.paidDate( entity.getPaidDate() );

        return repaymentResponseDTO.build();
    }

    @Override
    public Repayment toEntity(RepaymentRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Repayment.RepaymentBuilder repayment = Repayment.builder();

        repayment.amount( dto.getAmount() );
        repayment.loanAccountId( dto.getLoanAccountId() );
        repayment.mode( dto.getMode() );
        repayment.paidDate( dto.getPaidDate() );

        return repayment.build();
    }

    @Override
    public List<RepaymentResponseDTO> toResponseDTOList(List<Repayment> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RepaymentResponseDTO> list = new ArrayList<RepaymentResponseDTO>( entities.size() );
        for ( Repayment repayment : entities ) {
            list.add( toResponseDTO( repayment ) );
        }

        return list;
    }
}
