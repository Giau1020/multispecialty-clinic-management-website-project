package com.clinicManagement.ClinicManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private Long department_id;
    private LocalDate effectiveDate;
    private Long userId;
}
