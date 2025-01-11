package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.DTO.PrescriptionDTO;
import com.clinicManagement.ClinicManagement.model.MedicalRecord;
import com.clinicManagement.ClinicManagement.model.Prescription;
import com.clinicManagement.ClinicManagement.repository.MedicalRecordRepository;
import com.clinicManagement.ClinicManagement.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    //Hàm tạo đơn thuốc
    @Transactional
    public Prescription createPrescription(Long recordId, PrescriptionDTO prescription){
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Not found medicalRecord by id " + recordId) );

        Prescription prescription1 = Prescription.builder()
                .medicalRecord(medicalRecord)
                .notes(prescription.getNotes())
                .dosage(prescription.getDosage())
                .duration(prescription.getDuration())
                .medicationId(prescription.getMedicationId()).build();
        prescription1.setMedicalRecord(medicalRecord);
        return prescriptionRepository.save(prescription1);
    }

    //Hàm cập nhật đơn thuốc
    @Transactional
    public Prescription updatePrescription(Long prescriptionId, PrescriptionDTO prescriptionDTO){
        Prescription oldPrescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn thuốc với id " + prescriptionId));

        if(prescriptionDTO.getDosage() != null){
            oldPrescription.setDosage(prescriptionDTO.getDosage());
        }

        if(prescriptionDTO.getDuration() != null){
            oldPrescription.setDuration(prescriptionDTO.getDuration());
        }

        if(prescriptionDTO.getFrequency() != null){
            oldPrescription.setFrequency(prescriptionDTO.getFrequency());
        }

        if(prescriptionDTO.getMedicationId() != null){
            oldPrescription.setMedicationId(prescriptionDTO.getMedicationId());
        }

        if(prescriptionDTO.getNotes() != null){
            oldPrescription.setNotes(prescriptionDTO.getNotes());
        }

        return prescriptionRepository.save(oldPrescription);

    }
}
