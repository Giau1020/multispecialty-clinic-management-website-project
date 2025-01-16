package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.DTO.DetailScheduleDTO;
import com.clinicManagement.ClinicManagement.model.DetailSchedule;
import com.clinicManagement.ClinicManagement.model.Schedule;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.DetailScheduleRepository;
import com.clinicManagement.ClinicManagement.repository.ScheduleRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetailScheduleService {
    @Autowired
    private DetailScheduleRepository detailScheduleRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    // Hàm thêm detail_service
    public DetailSchedule createDetailSchedule(DetailScheduleDTO detailScheduleDTO){
        Schedule schedule = scheduleRepository.findById(detailScheduleDTO.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch làm việc với id "+ detailScheduleDTO.getScheduleId()));
        User user = userRepository.findById(detailScheduleDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không timg thấy user với id "+ detailScheduleDTO.getUserId()));
        DetailSchedule detailSchedule = DetailSchedule.builder()
                .user(user)
                .schedule(schedule)
                .workDate(detailScheduleDTO.getWorkDate())
                .startTime(detailScheduleDTO.getStartTime())
                .endTime(detailScheduleDTO.getEndTime())
                .status(detailScheduleDTO.getStatus())
                .notes(detailScheduleDTO.getNotes())
                .createdAt(LocalDateTime.now()).build();



       return detailScheduleRepository.save(detailSchedule);
    }

    //Hàm update detail_service
    public DetailSchedule updateDetailSchedule(Long id, DetailScheduleDTO detailScheduleDTO){
        DetailSchedule oldDetailSchedule = detailScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy detail của lịch làm việc by id= "+ id));

        if(detailScheduleDTO.getUserId() != null){
            User user = userRepository.findById(detailScheduleDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Không timg thấy user với id "+ detailScheduleDTO.getUserId()));
            oldDetailSchedule.setUser(user);
        }
        if(detailScheduleDTO.getScheduleId() != null){
            Schedule schedule = scheduleRepository.findById(detailScheduleDTO.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch làm việc với id "+ detailScheduleDTO.getScheduleId()));
            oldDetailSchedule.setSchedule(schedule);
        }

        if(detailScheduleDTO.getWorkDate() != null){
            oldDetailSchedule.setWorkDate(detailScheduleDTO.getWorkDate());
        }
        if(detailScheduleDTO.getStartTime() != null){
            oldDetailSchedule.setStartTime(detailScheduleDTO.getStartTime());
        }
        if(detailScheduleDTO.getEndTime() != null){
            oldDetailSchedule.setEndTime(detailScheduleDTO.getEndTime());
        }
        if(detailScheduleDTO.getNotes() != null){
            oldDetailSchedule.setNotes(detailScheduleDTO.getNotes());
        }
        if(detailScheduleDTO.getStatus() != null){
            oldDetailSchedule.setStatus(detailScheduleDTO.getStatus());
        }

        return detailScheduleRepository.save(oldDetailSchedule);

    }

    //Hàm xóa detail schedule
    public void deleteDetailSchedule(Long id){
        detailScheduleRepository.deleteById(id);
    }

}
