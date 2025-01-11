package com.clinicManagement.ClinicManagement.repository;


import com.clinicManagement.ClinicManagement.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
    List<InvoiceDetail> findByInvoiceInvoiceId(Long invoiceId);
}
