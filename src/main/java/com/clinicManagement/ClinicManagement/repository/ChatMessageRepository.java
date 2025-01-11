package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByConversationConversationId(Long conversationId);
    List<ChatMessage> findBySender(ChatMessage.SenderType sender);
}
