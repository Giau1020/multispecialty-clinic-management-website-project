package com.clinicManagement.ClinicManagement.request;

import com.clinicManagement.ClinicManagement.model.Address;
import com.clinicManagement.ClinicManagement.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter

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
