package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.model.ChatConversation;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.ChatConversationRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat_conversation")
public class ChatConversationController {

    @Autowired
    private ChatConversationRepository chatConversationRepository;

    @Autowired
    private UserRepository userRepository;

    //Hàm tạo bắt đầu conversation (end_conversation là null)
    @Transactional
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createChatConversation(@PathVariable Long userId){
        //Tìm kiếm user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        ChatConversation chatConversation = ChatConversation.builder()
                .user(user)
                .startedAt(LocalDateTime.now()).build();

        return ResponseEntity.ok(chatConversationRepository.save(chatConversation));

    }


    //Hàm kết thúc cuộc trò chuyênj
    @Transactional
    @PutMapping("/ended/{conversationId}")
    public ResponseEntity<?> endedChatConversation(@PathVariable Long conversationId){
        //Tìm kiếm conversation
        ChatConversation chatConversation = chatConversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc trò chuyện"));

        chatConversation.setEndedAt(LocalDateTime.now());
        chatConversationRepository.save(chatConversation);
        return ResponseEntity.ok("kết thúc cuộc trò chuyện thành công");
    }

    /////////////////////////////////
    //CÁC HÀM GET DANH SÁCH, GET THEO MÃ SỐ USERID, GET THEO THỜI GIAN (TÍNH BẰNG NGÀY GIỜ)
    @GetMapping("/getby_userid/{userId}")
    public ResponseEntity<?> getConversationByUserId(@PathVariable Long userId){
        List<ChatConversation> list = chatConversationRepository.findByUserId(userId);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/getby_time/{startDate}/{endDate}")
    public ResponseEntity<?> getConversationByTime(@PathVariable String startDate, @PathVariable String endDate){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);
             List<ChatConversation> list =   chatConversationRepository.findByStartedAtBetween(startDateTime, endDateTime);
             return ResponseEntity.ok(list);
    }

    @GetMapping("/getby_userId_time/{userId}/{startDate}/{endDate}")
    public ResponseEntity<?> getConversationsByUserIdAndDateRange(@PathVariable Long userId,@PathVariable String startDate,@PathVariable String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);
        List <ChatConversation> list = chatConversationRepository.findByUser_IdAndStartedAtBetween(userId, startDateTime, endDateTime);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getConversationById(@PathVariable Long id){
        return ResponseEntity.ok(chatConversationRepository.findById(id));
    }





}
