package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.DTO.ServicePricingHistoryDTO;
import com.clinicManagement.ClinicManagement.model.ServicePricingHistory;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.ServicePricingHistoryRepository;
import com.clinicManagement.ClinicManagement.repository.ServiceRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service


public class ServicePricingHistoryService {
    @Autowired
    private ServicePricingHistoryRepository servicePricingHistoryRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    //Hàm thêm servicePicinHistory
    public ServicePricingHistory create(ServicePricingHistoryDTO servicePricingHistoryDTO){
        com.clinicManagement.ClinicManagement.model.Service service =serviceRepository.findById(servicePricingHistoryDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Không tìm thây service ới id " + servicePricingHistoryDTO.getServiceId()));

        User user = userRepository.findById(servicePricingHistoryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thasy user với id" + servicePricingHistoryDTO.getUserId()));
        ServicePricingHistory servicePricingHistory = ServicePricingHistory.builder()
                .service(service)
                .price(servicePricingHistoryDTO.getPrice())
                .effectiveDate(servicePricingHistoryDTO.getEffectiveDate())
                .updatedBy(user).build();

        return servicePricingHistoryRepository.save(servicePricingHistory);
    }

}
