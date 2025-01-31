package com.clinicManagement.ClinicManagement.repository;


import com.clinicManagement.ClinicManagement.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByPatientUserId(Long patientId);
    List<Invoice> findByPaymentPaymentId(Long paymentId);
}
