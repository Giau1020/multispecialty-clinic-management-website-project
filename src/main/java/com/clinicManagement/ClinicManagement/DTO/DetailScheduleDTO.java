package com.clinicManagement.ClinicManagement.DTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailScheduleDTO {

        private Long scheduleId;
        private Long userId;
        private LocalDate workDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private String status;
        private String notes;

}
