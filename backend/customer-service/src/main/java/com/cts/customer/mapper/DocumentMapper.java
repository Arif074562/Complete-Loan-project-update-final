package com.cts.customer.mapper;

import com.cts.customer.dto.DocumentRequestDTO;
import com.cts.customer.dto.DocumentResponseDTO;
import com.cts.customer.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "documentId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "uploadedDate", ignore = true)
    DocumentResponseDTO toResponseDTO(Document entity);

    @Mapping(target = "documentId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "uploadedDate", ignore = true)
    Document toEntity(DocumentRequestDTO dto);

    List<DocumentResponseDTO> toResponseDTOList(List<Document> entities);
}
