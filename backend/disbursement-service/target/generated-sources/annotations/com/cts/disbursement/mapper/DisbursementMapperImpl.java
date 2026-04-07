package com.cts.disbursement.mapper;

import com.cts.disbursement.dto.DisbursementRequestDTO;
import com.cts.disbursement.dto.DisbursementResponseDTO;
import com.cts.disbursement.entity.Disbursement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:06:10+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DisbursementMapperImpl implements DisbursementMapper {

    @Override
    public DisbursementResponseDTO toResponseDTO(Disbursement entity) {
        if ( entity == null ) {
            return null;
        }

        DisbursementResponseDTO.DisbursementResponseDTOBuilder disbursementResponseDTO = DisbursementResponseDTO.builder();

        disbursementResponseDTO.disbursementId( entity.getDisbursementId() );
        disbursementResponseDTO.applicationId( entity.getApplicationId() );
        disbursementResponseDTO.amount( entity.getAmount() );
        disbursementResponseDTO.disbursementDate( entity.getDisbursementDate() );
        disbursementResponseDTO.status( entity.getStatus() );

        return disbursementResponseDTO.build();
    }

    @Override
    public Disbursement toEntity(DisbursementRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Disbursement.DisbursementBuilder disbursement = Disbursement.builder();

        disbursement.amount( dto.getAmount() );
        disbursement.applicationId( dto.getApplicationId() );
        disbursement.disbursementDate( dto.getDisbursementDate() );

        return disbursement.build();
    }

    @Override
    public List<DisbursementResponseDTO> toResponseDTOList(List<Disbursement> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DisbursementResponseDTO> list = new ArrayList<DisbursementResponseDTO>( entities.size() );
        for ( Disbursement disbursement : entities ) {
            list.add( toResponseDTO( disbursement ) );
        }

        return list;
    }
}
