package com.clinicManagement.ClinicManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatConversationDTO {
    private Long userId;
    private LocalDateTime started_at;
    private LocalDateTime ended_at;
}
