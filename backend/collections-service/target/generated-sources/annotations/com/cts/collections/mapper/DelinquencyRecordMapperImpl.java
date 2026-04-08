package com.cts.collections.mapper;

import com.cts.collections.dto.DelinquencyRequestDTO;
import com.cts.collections.dto.DelinquencyResponseDTO;
import com.cts.collections.entity.DelinquencyRecord;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T10:38:13+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DelinquencyRecordMapperImpl implements DelinquencyRecordMapper {

    @Override
    public DelinquencyResponseDTO toResponseDTO(DelinquencyRecord entity) {
        if ( entity == null ) {
            return null;
        }

        DelinquencyResponseDTO.DelinquencyResponseDTOBuilder delinquencyResponseDTO = DelinquencyResponseDTO.builder();

        delinquencyResponseDTO.bucket( entity.getBucket() );
        delinquencyResponseDTO.daysPastDue( entity.getDaysPastDue() );
        delinquencyResponseDTO.delinquencyId( entity.getDelinquencyId() );
        delinquencyResponseDTO.loanAccountId( entity.getLoanAccountId() );
        delinquencyResponseDTO.recordedDate( entity.getRecordedDate() );
        delinquencyResponseDTO.status( entity.getStatus() );

        return delinquencyResponseDTO.build();
    }

    @Override
    public DelinquencyRecord toEntity(DelinquencyRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DelinquencyRecord.DelinquencyRecordBuilder delinquencyRecord = DelinquencyRecord.builder();

        delinquencyRecord.bucket( dto.getBucket() );
        delinquencyRecord.daysPastDue( dto.getDaysPastDue() );
        delinquencyRecord.loanAccountId( dto.getLoanAccountId() );
        delinquencyRecord.recordedDate( dto.getRecordedDate() );

        return delinquencyRecord.build();
    }

    @Override
    public void updateEntity(DelinquencyRecord entity, DelinquencyRequestDTO dto) {
        if ( dto == null ) {
            return;
        }

        entity.setBucket( dto.getBucket() );
        entity.setDaysPastDue( dto.getDaysPastDue() );
        entity.setLoanAccountId( dto.getLoanAccountId() );
        entity.setRecordedDate( dto.getRecordedDate() );
    }

    @Override
    public List<DelinquencyResponseDTO> toResponseDTOList(List<DelinquencyRecord> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DelinquencyResponseDTO> list = new ArrayList<DelinquencyResponseDTO>( entities.size() );
        for ( DelinquencyRecord delinquencyRecord : entities ) {
            list.add( toResponseDTO( delinquencyRecord ) );
        }

        return list;
    }
}
