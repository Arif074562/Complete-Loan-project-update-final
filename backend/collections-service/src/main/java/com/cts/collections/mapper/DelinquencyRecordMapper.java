package com.cts.collections.mapper;

import com.cts.collections.dto.DelinquencyRequestDTO;
import com.cts.collections.dto.DelinquencyResponseDTO;
import com.cts.collections.entity.DelinquencyRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DelinquencyRecordMapper {

    DelinquencyResponseDTO toResponseDTO(DelinquencyRecord entity);

    @Mapping(target = "delinquencyId", ignore = true)
    @Mapping(target = "status", ignore = true)
    DelinquencyRecord toEntity(DelinquencyRequestDTO dto);

    @Mapping(target = "delinquencyId", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget DelinquencyRecord entity, DelinquencyRequestDTO dto);

    List<DelinquencyResponseDTO> toResponseDTOList(List<DelinquencyRecord> entities);
}
