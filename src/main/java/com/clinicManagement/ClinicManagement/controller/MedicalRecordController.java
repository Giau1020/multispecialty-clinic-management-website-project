package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.DTO.LabResultDTO;
import com.clinicManagement.ClinicManagement.DTO.MedicalRecordDTO;
import com.clinicManagement.ClinicManagement.DTO.PrescriptionDTO;
import com.clinicManagement.ClinicManagement.model.MedicalRecord;
import com.clinicManagement.ClinicManagement.model.Prescription;
import com.clinicManagement.ClinicManagement.repository.MedicalRecordRepository;
import com.clinicManagement.ClinicManagement.service.LabResultService;
import com.clinicManagement.ClinicManagement.service.MedicalRecordService;
import com.clinicManagement.ClinicManagement.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/medical_record")
public class MedicalRecordController {

    //Hàm tạo MedicalRecord
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PrescriptionService prescriptionService;

    // Hàm tạo hồ sơ bệnh án
    @PostMapping("/create")
    public ResponseEntity<?> createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord){
       Long id =  medicalRecordService.createMedicalRecord(medicalRecord);
        return ResponseEntity.ok(id);
    }
    //Hàm cập nhật hồ sơ bệnh án
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordDTO medicalRecordDTO){

        return ResponseEntity.ok().body(medicalRecordService.updateMedicalRecord(id,medicalRecordDTO));
    }

    // Hàm tạo đơn thuốc prescription
    @PostMapping("/create/prescription/{id}")
    public ResponseEntity<?> createPrescription(@PathVariable Long id,@RequestBody PrescriptionDTO prescription){
            prescriptionService.createPrescription(id, prescription);
            return ResponseEntity.ok("Thêm đơn thuốc thành công");
    }
    //Hàm cập nhataj đn thuốc
    @PostMapping("/update/prescription/{id}")
    public  ResponseEntity<?> updatePrescription(@PathVariable Long id, @RequestBody PrescriptionDTO prescriptionDTO){
        Prescription prescription = prescriptionService.updatePrescription(id, prescriptionDTO);
        return ResponseEntity.ok().body(prescription);
    }

    @Autowired
    private LabResultService labResultService;

    //Hàm tạo xét nghiệm
    @PostMapping("/create/lab_result/{id}")
    public ResponseEntity<?> createLabResult(@PathVariable Long id, @RequestBody LabResultDTO labResultDTO){
        return ResponseEntity.ok().body(labResultService.createLabResult(id, labResultDTO));
    }
    //Hàm cập nhật xét nghiệm
    @PostMapping("/update/lab_result/{id}")
    public ResponseEntity<?> updateLabResults(@PathVariable Long id, @RequestBody LabResultDTO labResultDTO){
        return ResponseEntity.ok().body(labResultService.updateLabResult(id, labResultDTO));
    }



    ///////////////
    //Các hàm get

    // Hàm hiển thị toàn bộ bệnh án
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @GetMapping("/searchAll")
    public ResponseEntity<?> getAllMedicalRecord(){
        return ResponseEntity.ok(medicalRecordRepository.findAll());
    }

    //Hiển thị danh sách bệnh án dựa trên doctorId
    @GetMapping("/searchByDoctor")
    public ResponseEntity<?> getListMedicalRecordByDoctorId(@RequestParam Long doctorId){
        List<MedicalRecord> list =  medicalRecordService.getMedicalRecordsByDoctorId(doctorId);
        return ResponseEntity.ok(list);
    }

    // Hiển thị danh sách bệnh án dựa trên patientId
    @GetMapping("/searchByPatient")
    public ResponseEntity<?> getListMedicalRecordByPatientId(@RequestParam Long patientId){
        List<MedicalRecord> list =  medicalRecordService.getMedicalRecordsByPatientId(patientId);
        return ResponseEntity.ok(list);
    }








}
