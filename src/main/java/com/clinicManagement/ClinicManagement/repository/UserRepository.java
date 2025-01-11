package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUserId(Long userId);

    Optional<User> findByPhoneNumber(String number);
    Optional<User> findByEmail(String email);
    Optional<User> findByIdCard(String idCard);
}
