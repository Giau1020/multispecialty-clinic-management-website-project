package com.clinicManagement.ClinicManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Lab_Results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_result_id")
    private Long labResultId;

    // Mối quan hệ Nhiều-Một với MedicalRecord
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    @JsonBackReference("labResultReference")
    private MedicalRecord medicalRecord;

    @Column(name = "test_type", nullable = false, length = 100)
    private String testType;

    @Column(name = "result", columnDefinition = "TEXT")
    private String result;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "img", nullable = true)
    private String img;



}
