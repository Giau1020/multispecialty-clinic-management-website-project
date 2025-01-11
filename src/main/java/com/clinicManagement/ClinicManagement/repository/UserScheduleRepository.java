package com.clinicManagement.ClinicManagement.repository;

// src/main/java/com/example/clinic/repository/UserScheduleRepository.java


import com.clinicManagement.ClinicManagement.model.UserSchedule;
import com.clinicManagement.ClinicManagement.model.UserScheduleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule, UserScheduleId> {
    List<UserSchedule> findByUserUserId(Long userId);
    List<UserSchedule> findByScheduleScheduleId(Long scheduleId);
}
