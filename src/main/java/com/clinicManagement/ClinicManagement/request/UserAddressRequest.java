package com.clinicManagement.ClinicManagement.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserAddressRequest {
      //  private User user;
       // private Address address;  // Thông tin địa chỉ
        private String username;
        private String email;
        private String passwordHash;
        private String phoneNumber;
        private String fullName;
        private LocalDate dateOfBirth;
        private String idCard;
        private String gender;

        private String street;
        private String ward;
        private String district;
        private String province;
        private String country;





}
