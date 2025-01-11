package com.clinicManagement.ClinicManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointment_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // ID duy nhất cho mỗi bản ghi (nếu cần)

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    @JsonBackReference
    private Appointment appointment;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // Số lượng dịch vụ trong cuộc hẹn

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Ghi chú cho dịch vụ trong cuộc hẹn


}
