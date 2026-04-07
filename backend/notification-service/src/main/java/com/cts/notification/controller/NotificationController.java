package com.cts.notification.controller;

import com.cts.notification.common.ApiResponse;
import com.cts.notification.dto.NotificationRequestDTO;
import com.cts.notification.dto.NotificationResponseDTO;
import com.cts.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> create(@Valid @RequestBody NotificationRequestDTO request) {
        log.info("POST /api/notifications");
        NotificationResponseDTO dto = notificationService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Notification created", dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<NotificationResponseDTO>>> getByUserId(@PathVariable Long userId,
                                                                                   Pageable pageable) {
        log.info("GET /api/notifications/user/{}", userId);
        Page<NotificationResponseDTO> page = notificationService.getByUserId(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved", page));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> markAsRead(@PathVariable Long id) {
        log.info("PATCH /api/notifications/{}/read", id);
        NotificationResponseDTO dto = notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read", dto));
    }

    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(@PathVariable Long userId) {
        log.info("PATCH /api/notifications/user/{}/read-all", userId);
        notificationService.markAllAsReadForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/notifications/{}", id);
        notificationService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted", null));
    }
    
    @PostMapping("/broadcast")
    public ResponseEntity<ApiResponse<Void>> broadcast(@Valid @RequestBody NotificationRequestDTO request) {
        log.info("POST /api/notifications/broadcast");
        notificationService.broadcast(request);
        return ResponseEntity.ok(ApiResponse.success("Notification broadcasted to all users", null));
    }
}
