package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.DTO.NotificationDTO;
import com.clinicManagement.ClinicManagement.DTO.NotificationRecipientDTO;
import com.clinicManagement.ClinicManagement.DTO.NotificationRecipientListDTO;
import com.clinicManagement.ClinicManagement.model.Notification;
import com.clinicManagement.ClinicManagement.model.NotificationRecipients;
import com.clinicManagement.ClinicManagement.model.NotificationRecipientsId;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.NotificationRecipientsRepository;
import com.clinicManagement.ClinicManagement.repository.NotificationRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    //Hàm tạo thông báo mới
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createNotification(@RequestBody  NotificationDTO notificationDTO){

        Notification notification = Notification.builder()
                .title(notificationDTO.getTitle())
                .notificationType(notificationDTO.getNotificationType())
                .message(notificationDTO.getMessage())
                .createdAt(LocalDateTime.now()).build();

        return ResponseEntity.ok(notificationRepository.save(notification));
    }

    //Hàm cập nhật thông báo dựa vào ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO){
        //tìm kiếm thông báo với id có tồn tại hay không
        Notification oldNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo với id " + id));

        //thêm các trường được đưa vào
        if(notificationDTO.getMessage() != null){
            oldNotification.setMessage(notificationDTO.getMessage());
        }

        if(notificationDTO.getTitle() != null){
            oldNotification.setTitle(notificationDTO.getTitle());
        }

        if(notificationDTO.getNotificationType() != null){
            oldNotification.setNotificationType(notificationDTO.getNotificationType());
        }

        return ResponseEntity.ok(notificationRepository.save(oldNotification));
    }

    // hàm xóa thông báo dựa vòa id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id){
        notificationRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }



    //////////////////////////////////////////////////////////////////////////////////////////////
    //Notification Recipient
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRecipientsRepository notificationRecipientsRepository;



    // hàm xác định thông báo đó gửi cho user nào (Tạo mới trong Notification Recipient)
    @PostMapping("/create/recipient")
    @Transactional
    public ResponseEntity<?>  createNotificationRecipient(
            @RequestBody NotificationRecipientDTO notificationRecipientDTO
    ){
        User user = userRepository.findById(notificationRecipientDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        Notification notification = notificationRepository.findById(notificationRecipientDTO.getNotificationId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        NotificationRecipientsId notificationRecipientsId = new NotificationRecipientsId(notificationRecipientDTO.getUserId(),notificationRecipientDTO.getNotificationId() );


        NotificationRecipients notificationRecipients = NotificationRecipients.builder()
                .id(notificationRecipientsId)
                .user(user)
                .notification(notification)
                .sendVia(notificationRecipientDTO.getSend_via())
                .isRead(notificationRecipientDTO.is_read()).build();
        notificationRecipientsRepository.save(notificationRecipients);
        return  ResponseEntity.ok("Thêm thành công");

    }

    //Hàm tạo notification recipient và list Userid với 1 notificationID
    @Transactional
    @PostMapping("/create/recipient/list")
    public ResponseEntity<?>  createNotificationRecipientList(
            @RequestBody NotificationRecipientListDTO notificationRecipientDTO
    ){

        List<Long> list = notificationRecipientDTO.getUserId();
        Notification notification = notificationRepository.findById(notificationRecipientDTO.getNotificationId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));

        for(Long id : list){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
            NotificationRecipientsId notificationRecipientsId =  new NotificationRecipientsId(id, notificationRecipientDTO.getNotificationId());

            NotificationRecipients notificationRecipients = NotificationRecipients.builder()
                    .id(notificationRecipientsId)
                    .user(user)
                    .notification(notification)
                    .sendVia(notificationRecipientDTO.getSend_via())
                    .isRead(notificationRecipientDTO.is_read()).build();
            notificationRecipientsRepository.save(notificationRecipients);

        }


        return  ResponseEntity.ok("Thêm thành công");

    }



    // Hàm kiểm tra khóa chính của NotificationRecipient
    public  boolean check(NotificationRecipientDTO notificationRecipientDTO){
        userRepository.findById(notificationRecipientDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        notificationRepository.findById(notificationRecipientDTO.getNotificationId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));

        return true;
    }




    public NotificationRecipients updateNotificationRecipientSV(Long userId, Long notificationId, NotificationRecipients updatedData) {
        return notificationRecipientsRepository.findById(new NotificationRecipientsId(userId, notificationId))
                .map(existingRecipient -> {
                    existingRecipient.setRead(updatedData.isRead());
                    existingRecipient.setSendVia(updatedData.getSendVia());
                    return notificationRecipientsRepository.save(existingRecipient);
                })
                .orElseThrow(() -> new IllegalArgumentException("NotificationRecipient not found"));
    }

    // Hàm cập nhật
    @Transactional
    @PutMapping("/update/recipient/{userId}/{notificationId}")
    public ResponseEntity<NotificationRecipients> updateNotificationRecipient(
            @PathVariable Long userId,
            @PathVariable Long notificationId,
            @RequestBody NotificationRecipients updatedData) {
        try {
            NotificationRecipients updatedRecipient = updateNotificationRecipientSV(userId, notificationId, updatedData);
            return ResponseEntity.ok(updatedRecipient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Hàm xóa
    @Transactional
    @DeleteMapping("/delete/recipient/{userId}/{notificationId}")
    public ResponseEntity<?> deleteNotificationRecipient(
            @PathVariable Long userId,
            @PathVariable Long notificaitonId
    ){
        NotificationRecipientsId notificationRecipientsId = new NotificationRecipientsId(userId, notificaitonId);
        notificationRecipientsRepository.deleteById(notificationRecipientsId);
        return ResponseEntity.ok("Xoóa thành công");
    }







}
