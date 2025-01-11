package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.ChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {
    List<ChatConversation> findByUserUserId(Long userId);
}
