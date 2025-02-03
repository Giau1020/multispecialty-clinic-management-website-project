package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.DTO.DetailScheduleDTO;
import com.clinicManagement.ClinicManagement.DTO.ScheduleDTO;
import com.clinicManagement.ClinicManagement.model.Schedule;
import com.clinicManagement.ClinicManagement.repository.DetailScheduleRepository;
import com.clinicManagement.ClinicManagement.repository.ScheduleRepository;
import com.clinicManagement.ClinicManagement.service.DetailScheduleService;
import lombok.Locked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    //hàm tạo lịch làm việc tổng mới
    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody  ScheduleDTO scheduleDTO){
        Schedule schedule = Schedule.builder()
                .name(scheduleDTO.getName())
                .description(scheduleDTO.getDescription()).build();
        return  ResponseEntity.ok(scheduleRepository.save(schedule));
    }

    //Hàm xóa lịch làm việc
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id){
        scheduleRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    //Hàm chỉnh sửa lịch làm việc
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch làm việc"));

        if(scheduleDTO.getName() != null){
            schedule.setName(scheduleDTO.getName());
        }
        if(scheduleDTO.getDescription()!= null){
            schedule.setDescription(scheduleDTO.getDescription());
        }

        return ResponseEntity.ok(scheduleRepository.save(schedule));

    }

    //Tìm kiếm
    @GetMapping("/searchAll")
    public ResponseEntity<?> getAllSchedule(){
        return ResponseEntity.ok(scheduleRepository.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Long id){
        return ResponseEntity.ok(scheduleRepository.findById(id));
    }

    //////////////////////////////////////////////////////////////
    //Phần của detailSchedule
    @Autowired
    private DetailScheduleService detailScheduleService;

    //Hàm tạo detailschedule
    @PostMapping("/detail/create")
    public  ResponseEntity<?> createDetailSchedule(@RequestBody DetailScheduleDTO detailScheduleDTO){
        return ResponseEntity.ok(detailScheduleService.createDetailSchedule(detailScheduleDTO));
    }

    //Hàm cập nhật detailSchedule
    @PutMapping("/detail/update/{id}")
    public ResponseEntity<?> updateDetailSchedule(@PathVariable Long id, @RequestBody DetailScheduleDTO detailScheduleDTO){
        return ResponseEntity.ok(detailScheduleService.updateDetailSchedule(id,detailScheduleDTO));
    }

    //Hàm xóa detailSchedule
    @DeleteMapping("/detail/delete/{id}")
    public ResponseEntity<?> deleteDetailSchedule(@PathVariable Long id){
        detailScheduleService.deleteDetailSchedule(id);
        return  ResponseEntity.ok("Xóa thành công detail schedule "+ id);
    }

    //Hàm tìm kiếm
    @Autowired
    private DetailScheduleRepository detailScheduleRepository;


    @GetMapping("/detail/searchAll")
    public ResponseEntity<?> getAllDetailSchedule(){
        return ResponseEntity.ok(detailScheduleRepository.findAll());
    }

    @GetMapping("/detail/search/{id}")
    public  ResponseEntity<?> getDetailScheduleById(@PathVariable Long id){
        return ResponseEntity.ok(detailScheduleRepository.findById(id));
    }




}
