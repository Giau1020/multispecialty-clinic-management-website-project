package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.DTO.DepartmentDTO;
import com.clinicManagement.ClinicManagement.service.DepartmentService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    //Hàm tao department
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@RequestBody  DepartmentDTO departmentDTO){
        return ResponseEntity.ok(departmentService.createDepartment(departmentDTO));
    }


    // Hàm updateDepartment
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO){
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }

    //Hàm xóa department
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    //Hàm hiển thị toàn bộ department
    @GetMapping
    public ResponseEntity<?> getAllDepartment(){
        return ResponseEntity.ok(departmentService.getAllDepartment());
    }
}
