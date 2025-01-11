package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //  Tìm tất cả các Schedule dựa trên userId.
    List<Schedule> findByUserUserId(Long userId);

    //Tìm tất cả các Schedule dựa trên ngày trong tuần.
    List<Schedule> findByDayOfWeek(DayOfWeek dayOfWeek);

    //Tìm tất cả các Schedule dựa trên ngày trong tuần và tình trạng sẵn sàng.
    List<Schedule> findByDayOfWeekAndIsAvailable(DayOfWeek dayOfWeek, Boolean isAvailable);
}
