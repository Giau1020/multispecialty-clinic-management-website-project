// src/main/java/com/clinicManagement/ClinicManagement/model/Appointment.java

package com.clinicManagement.ClinicManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Appointments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;



    // Bác sĩ của cuộc hẹn
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    // Bệnh nhân của cuộc hẹn
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    // MQH voi service thông qua bảng AppointmentService
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AppointmentServices> appointmentServices;


    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "status", nullable = false, length = 20)
    @Pattern(regexp = "Đã lên lịch|Chờ khám|Đã hoàn thành|Đã hủy", message = "Status must be Scheduled, Completed, or Canceled")
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



    // Phương thức tiện ích để thêm AppointmentService
    public void addAppointmentService(AppointmentServices appointmentServices) {
        this.appointmentServices.add(appointmentServices);
        appointmentServices.setAppointment(this);
    }

    // Phương thức tiện ích để loại bỏ AppointmentService
    public void removeAppointmentService(AppointmentServices appointmentServices) {
        this.appointmentServices.remove(appointmentServices);
        appointmentServices.setAppointment(null);
    }
}
