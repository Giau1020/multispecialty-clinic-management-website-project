package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.model.UserRole;
import com.clinicManagement.ClinicManagement.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    // Tìm tất cả UserRole theo userId
    List<UserRole> findByUserRoleIdUserId(Long userId);

    // Tìm tất cả UserRole theo roleId
    List<UserRole> findByUserRoleIdRoleId(Long roleId);
    boolean existsByUserUserIdAndRoleRoleId(Long userId, Long roleId);
    void deleteByUserUserIdAndRoleRoleId(Long userId, Long roleId);

}
