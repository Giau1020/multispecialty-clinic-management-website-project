package com.clinicManagement.ClinicManagement;

import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
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

	@Bean
	public CommandLineRunner commandLineRunner( UserRepository repo) {
		return runner -> {
			getUserById(repo);
		};
	}

	private void getUserById(UserRepository repo) {
		User user = repo.findById(1l).orElseThrow(() -> new RuntimeException("User does not exist"));
		System.out.println(user.getUserId());
		System.out.println(user.getEmail());
	}
//	@Bean
//	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
//		return runner -> {
////			createCourseAndStudent(appDAO);
////			findCourseAndStudents(appDAO);
////			findStudentAndCoursesById(appDAO);
////			addMoreCoursesForStudent(appDAO);
////			deleteCourse(appDAO);
//			deleteStudent(appDAO);
//		};
//	}

}
