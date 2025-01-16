package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.DetailSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DetailScheduleRepository extends JpaRepository<DetailSchedule, Long> {
    @Query(value = "SELECT * FROM work_schedule WHERE work_date = :searchDate",
            nativeQuery = true)
    List<DetailSchedule> findAllByWorkDate(@Param("searchDate") LocalDate searchDate);

    // Native Query tìm theo khoảng ngày (BETWEEN)
    @Query(value = "SELECT * FROM work_schedule WHERE work_date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    List<DetailSchedule> findAllByWorkDateBetween(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    /**
     * Tìm tất cả các DetailSchedule theo userId và đúng 1 ngày cụ thể.
     */
    @Query(value = "SELECT * FROM detail_schedule WHERE user_id = :userId AND work_date = :workDate", nativeQuery = true)
    List<DetailSchedule> findAllByUserIdAndWorkDate(@Param("userId") Integer userId,
                                                    @Param("workDate") LocalDate workDate);

    @Query(value = "SELECT * FROM detail_schedule WHERE user_id = :userId AND work_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<DetailSchedule> findAllByUserIdAndWorkDateBetween(@Param("userId") Integer userId,
                                                           @Param("startDate") LocalDate startDate,
                                                           @Param("endDate") LocalDate endDate);

    // Native Query tìm tất cả các DetailSchedule theo userId
    @Query(value = "SELECT * FROM detail_schedule WHERE user_id = :userId",
            nativeQuery = true)
    List<DetailSchedule> findAllByUserId(@Param("userId") Integer userId);




}
