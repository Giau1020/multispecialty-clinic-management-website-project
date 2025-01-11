package com.clinicManagement.ClinicManagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    // Mối quan hệ Nhiều-Một với Appointment
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "medication_id", nullable = false)
//    private MedicalRecord medicalRecord;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false, length = 50)
    @Pattern(regexp = "Cash|Bank", message = "Invalid payment method")
    private String paymentMethod;

    @Column(name = "payment_date", nullable = false, updatable = false)
    private LocalDateTime paymentDate;

    @Column(name = "status", nullable = false, length = 20)
    @Pattern(regexp = "Pending|Completed|Failed", message = "Status must be Pending, Completed, or Failed")
    private String status;

    @PrePersist
    protected void onCreate() {
        this.paymentDate = LocalDateTime.now();
    }
}
