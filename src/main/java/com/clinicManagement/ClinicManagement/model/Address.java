package com.clinicManagement.ClinicManagement.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "street", nullable = false, length = 255)
    private String street; // Số nhà, tên đường

    @Column(name = "ward", nullable = false, length = 100)
    private String ward; // Tên phường/xã

    @Column(name = "district", nullable = false, length = 100)
    private String district; // Tên quận/huyện

    @Column(name = "province", nullable = false, length = 100)
    private String province; // Tên tỉnh/thành phố



    @Column(name = "country", nullable = false, length = 100)
    private String country; // Quốc gia

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Ngăn vòng lặp khi chuyển đổi sang JSON
    private User user; // Tham chiếu đến bảng User



    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
