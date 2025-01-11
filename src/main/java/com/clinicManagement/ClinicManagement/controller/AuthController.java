package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import com.clinicManagement.ClinicManagement.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private  UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Xác thực người dùng
            Optional<User> optionalUser1 = userRepository.findByUsername(loginRequest.getUsername());
            Optional<User> optionalUser2 = userRepository.findByEmail(loginRequest.getUsername());
            User user = optionalUser1.orElse(optionalUser2.orElse(null));
            if (user != null) {
                if(Objects.equals(user.getPasswordHash(), loginRequest.getPassword())){
                    return ResponseEntity.ok("Đăng nhập thành công.");
                }
            }


            return ResponseEntity.badRequest()
                    .body("Tên đăng nhập hoặc mật khẩu không chính xác.");


        }  catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi đăng nhập.");
        }
    }
}
