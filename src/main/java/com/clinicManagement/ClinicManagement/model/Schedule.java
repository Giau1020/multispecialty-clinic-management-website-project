package com.clinicManagement.ClinicManagement.model;

// src/main/java/com/example/clinic/model/Schedule.java


import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 10)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    // Mối quan hệ nhiều-nhiều với User thông qua bảng phụ User_Schedules
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSchedule> userSchedules = new HashSet<>();


    // Nếu muốn tự động thiết lập giá trị mặc định cho isAvailable
    @PrePersist
    protected void onCreate() {
        if (this.isAvailable == null) {
            this.isAvailable = true;
        }
    }

}
