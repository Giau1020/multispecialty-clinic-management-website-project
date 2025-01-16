package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.DTO.ServiceDTO;
import com.clinicManagement.ClinicManagement.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    private ServicesService servicesService;

    // Hàm thêm service mới
    @PostMapping("/create")
    public ResponseEntity<?> createService(@RequestBody ServiceDTO serviceDTO){
        return ResponseEntity.ok(servicesService.createService(serviceDTO));
    }

    //hàm cập nhaatj service
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO){
        return ResponseEntity.ok(servicesService.updateService(id, serviceDTO));
    }

    //Hàm xóa service
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id){
        servicesService.deleteService(id);

        return ResponseEntity.ok("Xóa service thành công");
    }
}
