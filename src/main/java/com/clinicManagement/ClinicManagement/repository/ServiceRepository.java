package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    //Tìm Service dựa trên tên
    Optional<Service> findByName(String name);

    //Tìm tất cả các Service dựa trên departmentId.
    List<Service> findByDepartmentDepartmentId(Long departmentId);

    //Tìm tất cả các Service với giá lớn hơn hoặc bằng một giá trị nhất định.
    List<Service> findByPriceGreaterThanEqual(BigDecimal price);

}
