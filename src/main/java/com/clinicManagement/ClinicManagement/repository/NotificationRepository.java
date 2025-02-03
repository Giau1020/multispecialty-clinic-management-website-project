package com.clinicManagement.ClinicManagement.repository;


import com.clinicManagement.ClinicManagement.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
