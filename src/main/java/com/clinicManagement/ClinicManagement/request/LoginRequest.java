package com.clinicManagement.ClinicManagement.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Password không được để trống")
    private String password;

    public @NotBlank(message = "Username không được để trống") String getUsername() {
        return username;
    }

    public @NotBlank(message = "Password không được để trống") String getPassword() {
        return password;
    }
}
