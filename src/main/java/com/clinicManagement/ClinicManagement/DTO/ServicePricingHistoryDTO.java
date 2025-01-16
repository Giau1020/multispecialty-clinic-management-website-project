package com.clinicManagement.ClinicManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicePricingHistoryDTO {
    private Long serviceId;
    private BigDecimal price;
    private LocalDate effectiveDate;
    private Long userId;

}
