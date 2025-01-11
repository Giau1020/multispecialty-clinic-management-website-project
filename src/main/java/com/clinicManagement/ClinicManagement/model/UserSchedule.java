package com.clinicManagement.ClinicManagement.model;



import jakarta.persistence.*;
import lombok.*;
    import java.time.LocalDateTime;

@Entity
@Table(name = "User_Schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    UserSchedule {

    @EmbeddedId
    private UserScheduleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @PrePersist
    protected void onCreate() {
        this.assignedAt = LocalDateTime.now();
    }
}
