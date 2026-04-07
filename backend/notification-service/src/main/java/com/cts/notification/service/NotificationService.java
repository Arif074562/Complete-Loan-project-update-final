package com.cts.notification.service;

import com.cts.notification.dto.NotificationRequestDTO;
import com.cts.notification.dto.NotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationResponseDTO create(NotificationRequestDTO request);

    Page<NotificationResponseDTO> getByUserId(Long userId, Pageable pageable);

    NotificationResponseDTO markAsRead(Long id);

    void markAllAsReadForUser(Long userId);

    void delete(Long id);
    
    void broadcast(NotificationRequestDTO request);
}
