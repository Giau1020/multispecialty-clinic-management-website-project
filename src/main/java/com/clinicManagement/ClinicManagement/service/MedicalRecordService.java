package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.DTO.MedicalRecordDTO;
import com.clinicManagement.ClinicManagement.model.MedicalRecord;
import com.clinicManagement.ClinicManagement.model.Prescription;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.LabResultRepository;
import com.clinicManagement.ClinicManagement.repository.MedicalRecordRepository;
import com.clinicManagement.ClinicManagement.repository.PrescriptionRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserRepository userRepository;


    //Tạo bệnh án
    @Transactional
    public Long createMedicalRecord(MedicalRecordDTO medicalRecord){
        User patient = userRepository.findById(medicalRecord.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + medicalRecord.getPatientId()));
        User doctor = userRepository.findById(medicalRecord.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + medicalRecord.getDoctorId()));

        MedicalRecord medicalRecord1 = MedicalRecord.builder()
                .patient(patient)
                .doctor(doctor)
                .diagnosis(medicalRecord.getDiagnosis())
                .treatment(medicalRecord.getTreatment())
                .notes(medicalRecord.getNotes())
                .build();

        MedicalRecord medicalRecord2 = medicalRecordRepository.save(medicalRecord1);
        return medicalRecord2.getRecordId();
    }

    //update hồ sơ bệnh án
    @Transactional
    public MedicalRecord updateMedicalRecord(Long id, MedicalRecordDTO medicalRecordDTO){
        MedicalRecord oldMedicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bệnh án với id " + id));

        User doctor = userRepository.findByUserId(medicalRecordDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bác sĩ với id "+medicalRecordDTO.getDoctorId() ));

        User patient = userRepository.findById(medicalRecordDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bệnh nhân với id" + medicalRecordDTO.getPatientId()));

        //Thêm vào các trường mới
        oldMedicalRecord.setDoctor(doctor);

        oldMedicalRecord.setPatient(patient);

        if(medicalRecordDTO.getDiagnosis() != null){
            oldMedicalRecord.setDiagnosis(medicalRecordDTO.getDiagnosis());
        }

        if(medicalRecordDTO.getTreatment() != null){
            oldMedicalRecord.setDiagnosis(medicalRecordDTO.getTreatment());
        }

        if(medicalRecordDTO.getNotes() != null){
            oldMedicalRecord.setNotes(medicalRecordDTO.getNotes());
        }

        return medicalRecordRepository.save(oldMedicalRecord);
    }
}
