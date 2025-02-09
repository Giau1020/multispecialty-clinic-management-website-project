package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.DTO.DepartmentDTO;
import com.clinicManagement.ClinicManagement.model.Department;
import com.clinicManagement.ClinicManagement.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    //Hàm tạo mới department
    @Transactional
    public Department createDepartment(DepartmentDTO departmentDTO){
        Department department = Department.builder()
                .name(departmentDTO.getName())
                .description(departmentDTO.getDescription())
                .url(departmentDTO.getUrl())
                .services(null).build();
        return departmentRepository.save(department);
    }

    //Hàm chỉnh sửa department
    @Transactional
    public Department updateDepartment(Long id, DepartmentDTO departmentDTO){
        Department oldDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa có id "+ id));

        if(departmentDTO.getName() != null){
            oldDepartment.setName(departmentDTO.getName());
        }

        if(departmentDTO.getDescription() != null){
            oldDepartment.setDescription(departmentDTO.getDescription());
        }
        return departmentRepository.save(oldDepartment);
    }

    //Hàm xóa department
    @Transactional
    public void deleteDepartment(Long id){
        departmentRepository.deleteById(id);
    }

//    Hàm hiển thị toàn bộ department
    public List<Department> getAllDepartment(){
        return departmentRepository.findAll().stream()
                .distinct()
                .collect(Collectors.toList());
    }

    // Tìm Thông tin của department theo id
    public Optional<Department> getDepartmentById(Long id){
        return departmentRepository.findById(id);
    }


}
