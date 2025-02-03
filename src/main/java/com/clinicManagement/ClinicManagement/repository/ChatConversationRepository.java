package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.ChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {
    // Tìm theo user_id
    @Query(value = "SELECT * FROM Chat_Conversations WHERE user_id = :userId", nativeQuery = true)
    List<ChatConversation> findByUserId(@Param("userId") Long userId);

    // Tìm theo khoảng thời gian (started_at)
    @Query(value = "SELECT * FROM Chat_Conversations WHERE started_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<ChatConversation> findByStartedAtBetween(@Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    // Tìm theo user_id và khoảng thời gian
    @Query(value = "SELECT * FROM Chat_Conversations WHERE user_id = :userId AND started_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<ChatConversation> findByUser_IdAndStartedAtBetween(@Param("userId") Long userId,
                                                            @Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate);
}
