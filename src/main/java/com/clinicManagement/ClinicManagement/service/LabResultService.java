package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.DTO.LabResultDTO;
import com.clinicManagement.ClinicManagement.model.LabResult;
import com.clinicManagement.ClinicManagement.model.MedicalRecord;
import com.clinicManagement.ClinicManagement.repository.LabResultRepository;
import com.clinicManagement.ClinicManagement.repository.MedicalRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LabResultService {
    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    //Hàm tạo xét nghiệm
    @Transactional
    public LabResult createLabResult(Long id, LabResultDTO labResultDTO){
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ bệnh án"));
        LabResult labResult = LabResult.builder()
                .testType(labResultDTO.getTestType())
                .date(LocalDate.now())
                .img(labResultDTO.getImg())
                .result(labResultDTO.getResult())
                .medicalRecord(medicalRecord).build();

        return labResultRepository.save(labResult);
    }

    //Hàm cập nhật xét nghiêệm (viết thêm hàm tải ảnh vào folder)
    @Transactional
    public LabResult updateLabResult(Long labResultId, LabResultDTO labResultDTO){
        LabResult oldLabResult = labResultRepository.findById(labResultId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy xét nghiệm với id "+ labResultId));

        if(labResultDTO.getTestType() != null){
            oldLabResult.setTestType(labResultDTO.getTestType());
        }

        if(labResultDTO.getDate() != null){
            oldLabResult.setDate(labResultDTO.getDate());
        }

        if(labResultDTO.getImg() != null){
            oldLabResult.setImg(labResultDTO.getImg());
        }

        if(labResultDTO.getResult() != null){
            oldLabResult.setResult(labResultDTO.getResult());
        }

        return labResultRepository.save(oldLabResult);
    }

}
