package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LabResultRepository extends JpaRepository<LabResult, Long> {

}
