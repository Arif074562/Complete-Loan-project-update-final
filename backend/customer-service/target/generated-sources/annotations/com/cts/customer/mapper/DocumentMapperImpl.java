package com.cts.customer.mapper;

import com.cts.customer.dto.DocumentRequestDTO;
import com.cts.customer.dto.DocumentResponseDTO;
import com.cts.customer.entity.Document;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T15:21:39+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public DocumentResponseDTO toResponseDTO(Document entity) {
        if ( entity == null ) {
            return null;
        }

        DocumentResponseDTO.DocumentResponseDTOBuilder documentResponseDTO = DocumentResponseDTO.builder();

        documentResponseDTO.applicationId( entity.getApplicationId() );
        documentResponseDTO.documentType( entity.getDocumentType() );
        documentResponseDTO.fileUri( entity.getFileUri() );

        return documentResponseDTO.build();
    }

    @Override
    public Document toEntity(DocumentRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Document.DocumentBuilder document = Document.builder();

        document.applicationId( dto.getApplicationId() );
        document.documentType( dto.getDocumentType() );
        document.fileUri( dto.getFileUri() );

        return document.build();
    }

    @Override
    public List<DocumentResponseDTO> toResponseDTOList(List<Document> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DocumentResponseDTO> list = new ArrayList<DocumentResponseDTO>( entities.size() );
        for ( Document document : entities ) {
            list.add( toResponseDTO( document ) );
        }

        return list;
    }
}
