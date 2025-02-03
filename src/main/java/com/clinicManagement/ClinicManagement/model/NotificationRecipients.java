package com.clinicManagement.ClinicManagement.model;

import jakarta.persistence.*;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_recipients")
@Builder
public class NotificationRecipients {

    @EmbeddedId
    private NotificationRecipientsId id;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "send_via", nullable = false, length = 50)
    private String sendVia;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("notificationId")
    @JoinColumn(name = "notification_id")
    private Notification notification;
}

