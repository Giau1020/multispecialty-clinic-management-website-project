// src/main/java/com/example/clinic/model/UserScheduleId.java

package com.clinicManagement.ClinicManagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScheduleId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "schedule_id")
    private Long scheduleId;

    // Override equals() và hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserScheduleId that = (UserScheduleId) o;

        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (scheduleId != null ? scheduleId.hashCode() : 0);
        return result;
    }
}
