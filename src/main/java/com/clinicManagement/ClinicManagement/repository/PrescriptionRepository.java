package com.clinicManagement.ClinicManagement.repository;



import com.clinicManagement.ClinicManagement.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
   // List<Prescription> findByMedicalRecordRecordId(Long recordId);
}
