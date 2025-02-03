package com.clinicManagement.ClinicManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRecipientListDTO {
    private List<Long> userId;
    private Long notificationId;
    private boolean is_read;
    private String send_via;
}
