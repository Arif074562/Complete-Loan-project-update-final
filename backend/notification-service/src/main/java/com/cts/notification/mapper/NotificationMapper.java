package com.cts.notification.mapper;

import com.cts.notification.dto.NotificationRequestDTO;
import com.cts.notification.dto.NotificationResponseDTO;
import com.cts.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "notificationId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    NotificationResponseDTO toResponseDTO(Notification entity);

    @Mapping(target = "notificationId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    Notification toEntity(NotificationRequestDTO dto);

    List<NotificationResponseDTO> toResponseDTOList(List<Notification> entities);
}
