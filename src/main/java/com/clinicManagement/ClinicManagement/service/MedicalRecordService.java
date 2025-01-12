package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.DTO.AppointmentSimpleDTO;
import com.clinicManagement.ClinicManagement.DTO.MedicalRecordDTO;
import com.clinicManagement.ClinicManagement.model.*;
import com.clinicManagement.ClinicManagement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    //Tạo bệnh án
    @Transactional
    public Long createMedicalRecord(MedicalRecordDTO medicalRecord){
        User patient = userRepository.findById(medicalRecord.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + medicalRecord.getPatientId()));
        Doctor doctor = doctorRepository.findById(medicalRecord.getDoctorId())
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

        Doctor doctor = doctorRepository.findById(medicalRecordDTO.getDoctorId())
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

    public MedicalRecord getMedicalRecord(long id) {
        return medicalRecordRepository.findById(id).orElse(null);
    }



    // Hàm search {record_id, patient_id, doctor_id}
    public MedicalRecord getMedicalRecordById(Long id){
        return medicalRecordRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ bệnh án"));
    }

    public List<MedicalRecord> getMedicalRecordsByDoctorId(Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }
}
