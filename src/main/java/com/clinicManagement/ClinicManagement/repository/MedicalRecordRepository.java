package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
//    List<MedicalRecord> findByPatientUserId(Long patientId);
//    List<MedicalRecord> findByDoctorUserId(Long doctorId);
//    List<MedicalRecord> findByAppointmentAppointmentId(Long appointmentId);
}
