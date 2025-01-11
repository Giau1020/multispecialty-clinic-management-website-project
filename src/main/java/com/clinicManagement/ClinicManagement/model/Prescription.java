package com.clinicManagement.ClinicManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private Long prescriptionId;

    // Mối quan hệ Nhiều-Một với MedicalRecord
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    @JsonBackReference("prescriptionReference")
    private MedicalRecord medicalRecord;

    @Column(name = "medication_id", nullable = false, length = 100)
    private String medicationId; // ID từ API bên thứ 3

    @Column(name = "dosage", length = 100)
    private String dosage;

    @Column(name = "frequency", length = 50)
    private String  frequency;

    @Column(name = "duration", length = 50)
    private String duration;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
