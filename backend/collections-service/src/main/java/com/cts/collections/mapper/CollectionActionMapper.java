package com.cts.collections.mapper;

import com.cts.collections.dto.CollectionActionRequestDTO;
import com.cts.collections.dto.CollectionActionResponseDTO;
import com.cts.collections.entity.CollectionAction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollectionActionMapper {

    @Mapping(target = "actionId", ignore = true)
    CollectionActionResponseDTO toResponseDTO(CollectionAction entity);

    @Mapping(target = "actionId", ignore = true)
    CollectionAction toEntity(CollectionActionRequestDTO dto);

    List<CollectionActionResponseDTO> toResponseDTOList(List<CollectionAction> entities);
}
