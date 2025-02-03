package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.DTO.SpecialtyDTO;
import com.clinicManagement.ClinicManagement.model.Specialty;
import com.clinicManagement.ClinicManagement.repository.SpecialtyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialty")
public class SpecialtyController {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    //Hàm tạo specialties
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createSpecialties(@RequestBody SpecialtyDTO specialtyDTO){
        Specialty specialty =  Specialty.builder()
                .name(specialtyDTO.getName())
                .description(specialtyDTO.getDescription()).build();
        return  ResponseEntity.ok(specialtyRepository.save(specialty));
    }

    //Hàm cập nhật specialty
    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSpecialties(@PathVariable Long id, @RequestBody SpecialtyDTO specialtyDTO){
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Không tìm thấy chuyên khoa"));
        if(specialtyDTO.getName() != null){
            specialty.setName(specialtyDTO.getName());
        }

        if(specialtyDTO.getDescription() != null){
            specialty.setDescription(specialtyDTO.getDescription());
        }
        return  ResponseEntity.ok(specialtyRepository.save(specialty));
    }

    //Hàm xóa specialty
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpecialties(@PathVariable Long id){
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Không tìm thấy chuyên khoa"));

        specialtyRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công specialty với id là " +id);
    }

    //Hàm getAllSpecialty
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSpecialties(){
        List<Specialty> list = specialtyRepository.findAll();
        return ResponseEntity.ok(list);
    }

    // Hàm getSpecialtyById
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> getSpecialtyById(@PathVariable Long id){
        return ResponseEntity.ok(specialtyRepository.findById(id));
    }

}
