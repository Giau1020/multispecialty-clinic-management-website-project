package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
//    List<MedicalRecord> findByPatientUserId(Long patientId);
//    List<MedicalRecord> findByDoctorUserId(Long doctorId);
//    List<MedicalRecord> findByAppointmentAppointmentId(Long appointmentId);
    @Query(value = "SELECT * FROM Medical_Records WHERE doctor_id = :doctorId", nativeQuery = true)
    List<MedicalRecord> findByDoctorId(@Param("doctorId") Long doctorId);

    @Query(value = "SELECT * FROM Medical_Records WHERE patient_id = :patientId", nativeQuery = true)
    List<MedicalRecord> findByPatientId(@Param("patientId") Long patientId);
}
