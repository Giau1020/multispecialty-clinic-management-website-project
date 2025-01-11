package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.AppointmentServices;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentServicesRepository extends JpaRepository<AppointmentServices, Long> {
//    @Modifying
//    @Transactional
//   // @Query("DELETE FROM AppointmentService as a WHERE a.appointment.appointmentId = :appointmentId")
//    void deleteByAppointmentId(Long appointmentId);

//    @Modifying
//    @Transactional
//@Query(value = "DELETE FROM appointment_services WHERE appointment_id = :appointmentId", nativeQuery = true)
//    void deleteByAppointmentId( @Param("appointmentId") Long appointmentId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM appointment_services " +
            "WHERE appointment_id = :appointmentId " ,
            nativeQuery = true)
    void deleteByAppointmentId(@Param("appointmentId") Long appointmentId);
}
