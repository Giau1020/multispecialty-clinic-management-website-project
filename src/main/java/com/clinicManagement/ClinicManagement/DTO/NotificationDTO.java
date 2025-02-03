package com.clinicManagement.ClinicManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private String notificationType;
    private String message;
    private LocalDateTime created_at;
    private String title;
}
