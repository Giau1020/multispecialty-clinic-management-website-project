package com.clinicManagement.ClinicManagement.controller;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.DTO.AppointmentSimpleDTO;
import com.clinicManagement.ClinicManagement.model.Appointment;
import com.clinicManagement.ClinicManagement.model.Service;
import com.clinicManagement.ClinicManagement.repository.AppointmentRepository;
import com.clinicManagement.ClinicManagement.repository.ServiceRepository;
import com.clinicManagement.ClinicManagement.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentService appointmentService;

    // Lấy tất cả cuộc hẹn sắp xếp theo mới nhất
    @GetMapping
    public ResponseEntity<List<AppointmentSimpleDTO>> getAllAppointments() {
        List<AppointmentSimpleDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @Autowired
    private ServiceRepository serviceRepository;
    @GetMapping("/service/{id}")
    public ResponseEntity<?> getService(@PathVariable Long id) {
        Optional<Service> optionalService = serviceRepository.findById(id);

        if (optionalService.isPresent()) {
            return ResponseEntity.ok(optionalService.get());
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }



    //Lấy danh sách cuộc hẹn theo mã lịch hen
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id){
        try {
            AppointmentSimpleDTO appointmentSimpleDTO = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointmentSimpleDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Not found Appointment");
        }
    }
    // Hàm tìm kiếm danh sách cuộc hẹn theo lọc
    @GetMapping("/search")
    public List<AppointmentSimpleDTO> searchAppointments(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) Long appointmentId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return appointmentService.searchAppointments(
                doctorId, doctorName, patientId, patientName, serviceId, serviceName,
                appointmentId, status, startDate, endDate
        );
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment){

            appointmentService.updateAppointment(id, appointment);
            return  ResponseEntity.ok().body("Cập nhật thành công");

    }



    //Hàm tạo cuộc hẹn
    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        AppointmentSimpleDTO createdAppointment = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    //Hàm cập nhật trạng thái cho cuộc hẹn
    @PostMapping("/update/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        AppointmentSimpleDTO appointmentUpdated = appointmentService.updateStatusAppointment(id, status);
        return ResponseEntity.ok().body(appointmentUpdated);
    }



}
