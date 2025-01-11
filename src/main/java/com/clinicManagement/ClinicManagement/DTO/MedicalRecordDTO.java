package com.clinicManagement.ClinicManagement.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {

    private Long patientId;
    private Long doctorId;
    private String diagnosis;
    private String treatment;
    private String notes;
}