package com.clinicManagement.ClinicManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Table(name = "Doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @Column(name = "doctor_id")
    private Long doctorId;

    // Mối quan hệ Một-Một với User
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "doctor_id")
    private User user;

    // Mối quan hệ Nhiều-Một với Specialty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @Column(name = "qualifications", length = 255)
    private String qualifications;

    @Column(name = "experience_years")
    @Min(value = 0, message = "Experience years must be non-negative")
    private Integer experienceYears;

    // Mối quan hệ Nhiều-Một với Schedule
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
