package com.cts.disbursement.mapper;

import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.entity.RepaymentSchedule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T10:40:57+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RepaymentScheduleMapperImpl implements RepaymentScheduleMapper {

    @Override
    public RepaymentScheduleResponseDTO toResponseDTO(RepaymentSchedule entity) {
        if ( entity == null ) {
            return null;
        }

        RepaymentScheduleResponseDTO.RepaymentScheduleResponseDTOBuilder repaymentScheduleResponseDTO = RepaymentScheduleResponseDTO.builder();

        repaymentScheduleResponseDTO.dueDate( entity.getDueDate() );
        repaymentScheduleResponseDTO.installmentNo( entity.getInstallmentNo() );
        repaymentScheduleResponseDTO.interestComponent( entity.getInterestComponent() );
        repaymentScheduleResponseDTO.loanAccountId( entity.getLoanAccountId() );
        repaymentScheduleResponseDTO.principalComponent( entity.getPrincipalComponent() );
        repaymentScheduleResponseDTO.scheduleId( entity.getScheduleId() );
        repaymentScheduleResponseDTO.status( entity.getStatus() );
        repaymentScheduleResponseDTO.totalAmount( entity.getTotalAmount() );

        return repaymentScheduleResponseDTO.build();
    }

    @Override
    public List<RepaymentScheduleResponseDTO> toResponseDTOList(List<RepaymentSchedule> entities) {
        if ( entities == null ) {
            return null;
        }

        List<RepaymentScheduleResponseDTO> list = new ArrayList<RepaymentScheduleResponseDTO>( entities.size() );
        for ( RepaymentSchedule repaymentSchedule : entities ) {
            list.add( toResponseDTO( repaymentSchedule ) );
        }

        return list;
    }
}
