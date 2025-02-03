package com.clinicManagement.ClinicManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Checks;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRecipientsId implements Serializable {
    @Column(name = "notification_id")
    private Long notificationId;
    @Column(name = "user_id")
    private Long userId;
}

