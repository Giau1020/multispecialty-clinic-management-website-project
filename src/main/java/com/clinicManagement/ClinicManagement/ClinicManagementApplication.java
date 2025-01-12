package com.clinicManagement.ClinicManagement;

import com.clinicManagement.ClinicManagement.model.MedicalRecord;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import com.clinicManagement.ClinicManagement.service.MedicalRecordService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClinicManagementApplication {

	public static void main(String[] args) {
		 SpringApplication.run(ClinicManagementApplication.class, args);
	}



}
