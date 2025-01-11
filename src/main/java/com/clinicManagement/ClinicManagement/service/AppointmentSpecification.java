package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.model.Appointment;
import com.clinicManagement.ClinicManagement.model.Service;
import com.clinicManagement.ClinicManagement.model.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AppointmentSpecification {

    public static Specification<Appointment> hasDoctorId(Long doctorId) {
        return (root, query, criteriaBuilder) -> {
            if (doctorId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Appointment, User> doctor = root.join("doctor", JoinType.LEFT);
            return criteriaBuilder.equal(doctor.get("userId"), doctorId);
        };
    }

    public static Specification<Appointment> hasDoctorName(String doctorName) {
        return (root, query, criteriaBuilder) -> {
            if (doctorName == null || doctorName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Appointment, User> doctor = root.join("doctor", JoinType.LEFT);
            return criteriaBuilder.like(doctor.get("fullName"), "%" + doctorName + "%");
        };
    }

    public static Specification<Appointment> hasPatientId(Long patientId) {
        return (root, query, criteriaBuilder) -> {
            if (patientId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Appointment, User> patient = root.join("patient", JoinType.LEFT);
            return criteriaBuilder.equal(patient.get("userId"), patientId);
        };
    }

    public static Specification<Appointment> hasPatientName(String patientName) {
        return (root, query, criteriaBuilder) -> {
            if (patientName == null || patientName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Appointment, User> patient = root.join("patient", JoinType.LEFT);
            return criteriaBuilder.like(patient.get("fullName"), "%" + patientName + "%");
        };
    }

    public static Specification<Appointment> hasServiceId(Long serviceId) {
        return (root, query, criteriaBuilder) -> {
            if (serviceId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Appointment, ?> appointmentServices = root.join("appointmentServices", JoinType.LEFT);
            Join<?, Service> service = appointmentServices.join("service", JoinType.LEFT);
            return criteriaBuilder.equal(service.get("serviceId"), serviceId);
        };
    }

    public static Specification<Appointment> hasServiceName(String serviceName) {
        return (root, query, criteriaBuilder) -> {
            if (serviceName == null || serviceName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Appointment, ?> appointmentServices = root.join("appointmentServices", JoinType.LEFT);
            Join<?, Service> service = appointmentServices.join("service", JoinType.LEFT);
            return criteriaBuilder.like(service.get("name"), "%" + serviceName + "%");
        };
    }

    public static Specification<Appointment> hasAppointmentId(Long appointmentId) {
        return (root, query, criteriaBuilder) -> {
            if (appointmentId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("appointmentId"), appointmentId);
        };
    }

    public static Specification<Appointment> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Appointment> appointmentDateAfter(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("appointmentDate"), startDate);
        };
    }

    public static Specification<Appointment> appointmentDateBefore(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (endDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("appointmentDate"), endDate);
        };
    }
}
