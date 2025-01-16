package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.DTO.ServiceDTO;
import com.clinicManagement.ClinicManagement.DTO.ServicePricingHistoryDTO;
import com.clinicManagement.ClinicManagement.model.Department;
import com.clinicManagement.ClinicManagement.model.ServicePricingHistory;
import com.clinicManagement.ClinicManagement.repository.DepartmentRepository;
import com.clinicManagement.ClinicManagement.repository.ServicePricingHistoryRepository;
import com.clinicManagement.ClinicManagement.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ServicesService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    //Hàm thêm service
    @Transactional
    public com.clinicManagement.ClinicManagement.model.Service createService(ServiceDTO serviceDTO){
        Department department = departmentRepository.findById(serviceDTO.getDepartment_id())
                .orElseThrow(()-> new RuntimeException("Không tìm thấy khoa hợp lệ"));
        com.clinicManagement.ClinicManagement.model.Service service = com.clinicManagement.ClinicManagement.model.Service.builder()
                .name(serviceDTO.getName())
                .description(serviceDTO.getDescription())
                .price(serviceDTO.getPrice())
                .duration(serviceDTO.getDuration())
                .department(department).build();

        ServicePricingHistoryDTO servicePricingHistoryDTO = ServicePricingHistoryDTO.builder()
                .serviceId(service.getServiceId())
                .effectiveDate(serviceDTO.getEffectiveDate())
                .price(serviceDTO.getPrice())
                .userId(serviceDTO.getUserId()).build();

        servicePricingHistoryService.create(servicePricingHistoryDTO);

        return serviceRepository.save(service);
    }
    @Autowired
    private ServicePricingHistoryService servicePricingHistoryService;


    // Hàm cập nhật service
    @Transactional
    public com.clinicManagement.ClinicManagement.model.Service updateService(long id, ServiceDTO serviceDTO){
        com.clinicManagement.ClinicManagement.model.Service oldService = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy service với id "+ id));
        if(serviceDTO.getName() != null){
            oldService.setName(serviceDTO.getName());
        }

        if(serviceDTO.getDescription() != null){
            oldService.setDescription(serviceDTO.getDescription());
        }

        if(serviceDTO.getDuration() != 0){
            oldService.setDuration(serviceDTO.getDuration());
        }

        if(serviceDTO.getDepartment_id() != null){
            Department department = departmentRepository.findById(serviceDTO.getDepartment_id())
                            .orElseThrow(() -> new RuntimeException("Không tìm tấy department"));
            oldService.setDepartment(department);
        }

        if(serviceDTO.getPrice() != null && !serviceDTO.getPrice().equals(oldService.getPrice())){
            oldService.setPrice(serviceDTO.getPrice());

            ServicePricingHistoryDTO servicePricingHistoryDTO = ServicePricingHistoryDTO.builder()
                    .serviceId(oldService.getServiceId())
                    .effectiveDate(serviceDTO.getEffectiveDate())
                    .price(serviceDTO.getPrice())
                    .userId(serviceDTO.getUserId()).build();

            servicePricingHistoryService.create(servicePricingHistoryDTO);

        }
        return serviceRepository.save(oldService);


    }

    //Hàm xóa service
    public void deleteService(Long id){
        serviceRepository.deleteById(id);
    }

}
