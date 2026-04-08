package com.cts.collections.mapper;

import com.cts.collections.dto.CollectionActionRequestDTO;
import com.cts.collections.dto.CollectionActionResponseDTO;
import com.cts.collections.entity.CollectionAction;
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
public class CollectionActionMapperImpl implements CollectionActionMapper {

    @Override
    public CollectionActionResponseDTO toResponseDTO(CollectionAction entity) {
        if ( entity == null ) {
            return null;
        }

        CollectionActionResponseDTO.CollectionActionResponseDTOBuilder collectionActionResponseDTO = CollectionActionResponseDTO.builder();

        collectionActionResponseDTO.actionDate( entity.getActionDate() );
        collectionActionResponseDTO.actionType( entity.getActionType() );
        collectionActionResponseDTO.loanAccountId( entity.getLoanAccountId() );
        collectionActionResponseDTO.notes( entity.getNotes() );
        collectionActionResponseDTO.performedBy( entity.getPerformedBy() );

        return collectionActionResponseDTO.build();
    }

    @Override
    public CollectionAction toEntity(CollectionActionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CollectionAction.CollectionActionBuilder collectionAction = CollectionAction.builder();

        collectionAction.actionDate( dto.getActionDate() );
        collectionAction.actionType( dto.getActionType() );
        collectionAction.loanAccountId( dto.getLoanAccountId() );
        collectionAction.notes( dto.getNotes() );
        collectionAction.performedBy( dto.getPerformedBy() );

        return collectionAction.build();
    }

    @Override
    public List<CollectionActionResponseDTO> toResponseDTOList(List<CollectionAction> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CollectionActionResponseDTO> list = new ArrayList<CollectionActionResponseDTO>( entities.size() );
        for ( CollectionAction collectionAction : entities ) {
            list.add( toResponseDTO( collectionAction ) );
        }

        return list;
    }
}
