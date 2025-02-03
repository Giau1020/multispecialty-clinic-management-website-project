package com.clinicManagement.ClinicManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRecipientDTO {

    private Long userId;
    private Long notificationId;
    private boolean is_read;
    private String send_via;
}
