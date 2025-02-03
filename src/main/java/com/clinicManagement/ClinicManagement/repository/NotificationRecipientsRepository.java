package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.NotificationRecipients;
import com.clinicManagement.ClinicManagement.model.NotificationRecipientsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRecipientsRepository extends JpaRepository<NotificationRecipients, NotificationRecipientsId> {
}
