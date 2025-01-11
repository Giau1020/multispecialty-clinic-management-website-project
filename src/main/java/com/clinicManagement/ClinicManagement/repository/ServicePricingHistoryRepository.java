package com.clinicManagement.ClinicManagement.repository;


import com.clinicManagement.ClinicManagement.model.ServicePricingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicePricingHistoryRepository extends JpaRepository<ServicePricingHistory, Long> {
    List<ServicePricingHistory> findByServiceServiceIdOrderByEffectiveDateDesc(Long serviceId);
}
