package com.cts.underwriting.mapper;

import com.cts.underwriting.dto.CreditScoreRequestDTO;
import com.cts.underwriting.dto.CreditScoreResponseDTO;
import com.cts.underwriting.entity.CreditScore;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T10:41:03+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CreditScoreMapperImpl implements CreditScoreMapper {

    @Override
    public CreditScoreResponseDTO toResponseDTO(CreditScore entity) {
        if ( entity == null ) {
            return null;
        }

        CreditScoreResponseDTO.CreditScoreResponseDTOBuilder creditScoreResponseDTO = CreditScoreResponseDTO.builder();

        creditScoreResponseDTO.applicationId( entity.getApplicationId() );
        creditScoreResponseDTO.generatedDate( entity.getGeneratedDate() );
        creditScoreResponseDTO.reportRef( entity.getReportRef() );
        creditScoreResponseDTO.scoreId( entity.getScoreId() );
        creditScoreResponseDTO.scoreValue( entity.getScoreValue() );

        return creditScoreResponseDTO.build();
    }

    @Override
    public CreditScore toEntity(CreditScoreRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CreditScore.CreditScoreBuilder creditScore = CreditScore.builder();

        creditScore.applicationId( dto.getApplicationId() );
        creditScore.reportRef( dto.getReportRef() );
        creditScore.scoreValue( dto.getScoreValue() );

        return creditScore.build();
    }

    @Override
    public List<CreditScoreResponseDTO> toResponseDTOList(List<CreditScore> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CreditScoreResponseDTO> list = new ArrayList<CreditScoreResponseDTO>( entities.size() );
        for ( CreditScore creditScore : entities ) {
            list.add( toResponseDTO( creditScore ) );
        }

        return list;
    }
}
