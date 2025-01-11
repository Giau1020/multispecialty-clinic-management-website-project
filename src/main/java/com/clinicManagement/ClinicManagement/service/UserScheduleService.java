package com.clinicManagement.ClinicManagement.service;
// src/main/java/com/example/clinic/service/UserScheduleService.java

import com.clinicManagement.ClinicManagement.model.Schedule;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.model.UserSchedule;
import com.clinicManagement.ClinicManagement.model.UserScheduleId;
import com.clinicManagement.ClinicManagement.repository.ScheduleRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import com.clinicManagement.ClinicManagement.repository.UserScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserScheduleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private  UserScheduleRepository userScheduleRepository;

    @Transactional
    public Optional<User> addScheduleToUser(Long userId, Long scheduleId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Schedule> scheduleOpt = scheduleRepository.findById(scheduleId);
        if (userOpt.isPresent() && scheduleOpt.isPresent()) {
            User user = userOpt.get();
            Schedule schedule = scheduleOpt.get();

            UserScheduleId id = new UserScheduleId(userId, scheduleId);
            UserSchedule userSchedule = UserSchedule.builder()
                    .id(id)
                    .user(user)
                    .schedule(schedule)
                    .assignedAt(LocalDateTime.now())
                    .build();

            userScheduleRepository.save(userSchedule);

            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<User> removeScheduleFromUser(Long userId, Long scheduleId) {
        UserScheduleId id = new UserScheduleId(userId, scheduleId);
        Optional<UserSchedule> userScheduleOpt = userScheduleRepository.findById(id);
        if (userScheduleOpt.isPresent()) {
            userScheduleRepository.delete(userScheduleOpt.get());
            return userRepository.findById(userId);
        }
        return Optional.empty();
    }
}
