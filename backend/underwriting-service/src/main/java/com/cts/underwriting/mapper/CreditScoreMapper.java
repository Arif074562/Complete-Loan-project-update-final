package com.cts.underwriting.mapper;

import com.cts.underwriting.dto.CreditScoreRequestDTO;
import com.cts.underwriting.dto.CreditScoreResponseDTO;
import com.cts.underwriting.entity.CreditScore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditScoreMapper {

    CreditScoreResponseDTO toResponseDTO(CreditScore entity);

    @Mapping(target = "scoreId", ignore = true)
    @Mapping(target = "generatedDate", ignore = true)
    CreditScore toEntity(CreditScoreRequestDTO dto);

    List<CreditScoreResponseDTO> toResponseDTOList(List<CreditScore> entities);
}
