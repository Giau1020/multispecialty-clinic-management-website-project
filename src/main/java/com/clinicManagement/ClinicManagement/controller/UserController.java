package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import com.clinicManagement.ClinicManagement.request.UserAddressRequest;
import com.clinicManagement.ClinicManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //Hàm tạo thông tin bệnh nhân mới
    @PostMapping("/create/patient")
    public ResponseEntity<?> createUserPatient(@RequestBody UserAddressRequest userAddressRequest){
        userService.createUser(userAddressRequest, "patient");
        return ResponseEntity.ok().body("Thêm bệnh nhân thành công");
    }
    @PostMapping("/create/doctor")
    public ResponseEntity<?> createUserDoctor(@RequestBody UserAddressRequest userAddressRequest){
        userService.createUser(userAddressRequest, "doctor");
        return ResponseEntity.ok().body("Thêm thông tin bác sĩ thành công");
    }
    @PostMapping("/create/staff")
    public ResponseEntity<?> createUserStaff(@RequestBody UserAddressRequest userAddressRequest){
        userService.createUser(userAddressRequest, "staff");
        return ResponseEntity.ok().body("Thêm thoong tin nhân viên thành công");
    }

//    //Hàm update thông tin user
//    @PostMapping("/update/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable Long id,@RequestBody UserAddressRequest userAddressRequest){
//       User user =  userService.updateUser(id, userAddressRequest.getUser(), userAddressRequest.getAddress());
//        return ResponseEntity.ok().body(user);
//    }

    //Hàm tìm kiếm thông tin user
    @GetMapping("/search/{str}")
    public ResponseEntity<?> searchUser(@PathVariable String str){
        User user = userService.findUser(str);
        return ResponseEntity.ok().body(user);
    }

}
