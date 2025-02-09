package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import com.clinicManagement.ClinicManagement.repository.UserRoleRepository;
import com.clinicManagement.ClinicManagement.request.JWTReturn;
import com.clinicManagement.ClinicManagement.request.LoginRequest;
import com.clinicManagement.ClinicManagement.request.UserAddressRequest;
import com.clinicManagement.ClinicManagement.service.JwtService;
import com.clinicManagement.ClinicManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRoleRepository userRoleRepository;


    //Hàm tạo thông tin bệnh nhân mới
    @PostMapping("/create/patient")
    public ResponseEntity<?> createUserPatient(@RequestBody UserAddressRequest userAddressRequest){
       userService.createUser(userAddressRequest, "patient");

        return ResponseEntity.ok().body("Tạo thông tin bệnh nhân thành công");
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

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found")); // Tìm user trong database

        return ResponseEntity.ok(user); // Trả về thông tin user
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest user){
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        User user1 = userRepository.findByUsername(user.getUsername())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//
//        if(authentication.isAuthenticated()){
//            // Giả sử user1.getUserRoles() trả về Set<UserRole>
//            String roles = user1.getUserRoles()
//                    .stream()
//                    .map(userRole -> userRole.getRole().getRoleName()) // Lấy tên role từ đối tượng Role
//                    .collect(Collectors.joining(","));
//
//            String token = jwtService.generateToken(user1.getUsername(), roles);
//            String role = jwtService.extractRole(token);
//
//            JWTReturn jwtReturn = JWTReturn.builder()
//                    .token(token)
//                    .role(role).build();
//            return ResponseEntity.ok(jwtReturn);
//
//        }
//        else {
//           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        User user1 = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (authentication.isAuthenticated()) {
            // Giả sử user1.getUserRoles() trả về Set<UserRole>
            String roles = userRoleRepository.findByUserRoleIdUserId(user1.getUserId())
                    .stream()
                    .map(userRole -> {
                        if (userRole.getRole() != null) {
                            return userRole.getRole().getRoleName(); // Kiểm tra xem role có null không
                        }
                        return "No Role"; // Trả về thông báo nếu role là null
                    })
                    .collect(Collectors.joining(","));


            String token = jwtService.generateToken(user1.getUsername(), roles);
           // String role = jwtService.extractRole(token);

            JWTReturn jwtReturn = JWTReturn.builder()
                    .token(token)
                    .role(roles)
                    .build();

            return ResponseEntity.ok(jwtReturn);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        }
    }


}
