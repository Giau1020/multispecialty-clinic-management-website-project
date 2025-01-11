package com.clinicManagement.ClinicManagement.repository;

import com.clinicManagement.ClinicManagement.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
