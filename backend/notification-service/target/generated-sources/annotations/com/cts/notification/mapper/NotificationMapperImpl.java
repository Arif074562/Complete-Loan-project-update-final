package com.cts.notification.mapper;

import com.cts.notification.dto.NotificationRequestDTO;
import com.cts.notification.dto.NotificationResponseDTO;
import com.cts.notification.entity.Notification;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:26:42+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationResponseDTO toResponseDTO(Notification entity) {
        if ( entity == null ) {
            return null;
        }

        NotificationResponseDTO.NotificationResponseDTOBuilder notificationResponseDTO = NotificationResponseDTO.builder();

        notificationResponseDTO.category( entity.getCategory() );
        notificationResponseDTO.message( entity.getMessage() );
        notificationResponseDTO.userId( entity.getUserId() );

        return notificationResponseDTO.build();
    }

    @Override
    public Notification toEntity(NotificationRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Notification.NotificationBuilder notification = Notification.builder();

        notification.category( dto.getCategory() );
        notification.message( dto.getMessage() );
        notification.userId( dto.getUserId() );

        return notification.build();
    }

    @Override
    public List<NotificationResponseDTO> toResponseDTOList(List<Notification> entities) {
        if ( entities == null ) {
            return null;
        }

        List<NotificationResponseDTO> list = new ArrayList<NotificationResponseDTO>( entities.size() );
        for ( Notification notification : entities ) {
            list.add( toResponseDTO( notification ) );
        }

        return list;
    }
}
