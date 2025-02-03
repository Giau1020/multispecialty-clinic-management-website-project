package com.clinicManagement.ClinicManagement.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;



    @Column(name = "notification_type", nullable = false, length = 50)
    private String notificationType;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;


    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationRecipients> notificationsRecipients = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Enum cho Notification Type
    public enum NotificationType {
        APPOINTMENT,
        PAYMENT,
        GENERAL
    }
}
