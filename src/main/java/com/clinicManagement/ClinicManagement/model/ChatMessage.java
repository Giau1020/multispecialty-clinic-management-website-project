package com.clinicManagement.ClinicManagement.model;
// src/main/java/com/example/clinic/model/ChatMessage.java


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Chat_Messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    // Mối quan hệ Nhiều-Một với ChatConversation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ChatConversation conversation;

    @Column(name = "sender", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SenderType sender;

    @Column(name = "message_content", nullable = false, columnDefinition = "TEXT")
    private String messageContent;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Enum cho Sender Type
    public enum SenderType {
        USER,
        BOT,
        STAFF
    }
}
