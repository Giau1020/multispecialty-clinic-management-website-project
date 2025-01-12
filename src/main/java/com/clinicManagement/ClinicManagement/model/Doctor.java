package com.clinicManagement.ClinicManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "doctor_id")
//    private User user;

    // Mối quan hệ Nhiều-Một với Specialty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @Column(name = "qualifications", length = 255)
    private String qualifications;

    @Column(name = "experience_years")
    @Min(value = 0, message = "Experience years must be non-negative")
    private Integer experienceYears;
//
//    // Mối quan hệ Nhiều-Một với Schedule
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "schedule_id")
//    private Schedule schedule;

    // Quan hệ One-to-Many: Là bệnh nhân trong nhiều cuộc hẹn
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private List<MedicalRecord> doctorMedicalRecords = new ArrayList<>();
}
