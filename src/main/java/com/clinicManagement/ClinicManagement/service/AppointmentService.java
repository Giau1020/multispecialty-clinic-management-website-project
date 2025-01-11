package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.DTO.AppointmentSimpleDTO;
import com.clinicManagement.ClinicManagement.model.Appointment;
import com.clinicManagement.ClinicManagement.model.AppointmentServices;
import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.repository.AppointmentRepository;
import com.clinicManagement.ClinicManagement.repository.AppointmentServicesRepository;
import com.clinicManagement.ClinicManagement.repository.ServiceRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<AppointmentSimpleDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toAppointmentSimpleDTO)
                .collect(Collectors.toList());
    }

    public AppointmentSimpleDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment with ID " + id + " not found"));
        return toAppointmentSimpleDTO(appointment);
    }



    private AppointmentSimpleDTO toAppointmentSimpleDTO(Appointment appointment) {
        return new AppointmentSimpleDTO(
                appointment.getAppointmentId(),
                appointment.getDoctor().getUserId(), // Lấy doctorId từ thực thể User
                appointment.getPatient().getUserId(), // Lấy patientId từ thực thể User
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                appointment.getNotes()
        );
    }

    ////////////
    //Hàm tìm kiếm appointment dựa vào 1 hoặc nhiều tiêu chí

    public List<AppointmentSimpleDTO> searchAppointments(
            Long doctorId, String doctorName, Long patientId, String patientName,
            Long serviceId, String serviceName, Long appointmentId,
            String status,
            String startDate, String endDate
    ) {
        Specification<Appointment> spec = Specification.where(AppointmentSpecification.hasDoctorId(doctorId))
                .and(AppointmentSpecification.hasDoctorName(doctorName))
                .and(AppointmentSpecification.hasPatientId(patientId))
                .and(AppointmentSpecification.hasPatientName(patientName))
                .and(AppointmentSpecification.hasServiceId(serviceId))
                .and(AppointmentSpecification.hasServiceName(serviceName))
                .and(AppointmentSpecification.hasAppointmentId(appointmentId))
                .and(AppointmentSpecification.hasStatus(status))
                .and(AppointmentSpecification.appointmentDateAfter(parseDate(startDate)))
                .and(AppointmentSpecification.appointmentDateBefore(parseDate(endDate)));

        List<Appointment> appointments = appointmentRepository.findAll(spec);

        return appointments.stream()
                .map(this::toAppointmentSimpleDTO)
                .collect(Collectors.toList());
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date); // Định dạng ISO 8601 (yyyy-MM-dd)
    }




    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentServicesRepository appointmentServicesRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Transactional
    public AppointmentSimpleDTO createAppointment(Appointment appointment) {
        // 1. Lấy thông tin bác sĩ từ cơ sở dữ liệu
        AppointmentSimpleDTO result = null;
        try {


        User doctor = userRepository.findById(appointment.getDoctor().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + appointment.getDoctor().getUserId()));

        // 2. Lấy thông tin bệnh nhân từ cơ sở dữ liệu
        User patient = userRepository.findById(appointment.getPatient().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + appointment.getPatient().getUserId()));

        // 3. Tạo và thiết lập đối tượng Appointment
        Appointment app = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .notes(appointment.getNotes())
                .build();

        // 4. Lưu Appointment vào cơ sở dữ liệu để nhận được appointmentId
        Appointment savedAppointment = appointmentRepository.save(app);

        // 5. Xử lý các AppointmentService liên quan
        if (appointment.getAppointmentServices() != null && !appointment.getAppointmentServices().isEmpty()) {
            for (AppointmentServices appointmentService : appointment.getAppointmentServices()) {
                // a. Lấy Service từ cơ sở dữ liệu
                com.clinicManagement.ClinicManagement.model.Service service = serviceRepository.findById(appointmentService.getService().getServiceId())
                        .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + appointmentService.getService().getServiceId()));

                // b. Thiết lập Service cho AppointmentService
                appointmentService.setService(service);

                // c. Thiết lập Appointment cho AppointmentService
                appointmentService.setAppointment(savedAppointment);

                // d. Lưu AppointmentService vào cơ sở dữ liệu
                 appointmentServicesRepository.save(appointmentService);
            }
        }

        // 6. Trả về Appointment đã lưu
      result = toAppointmentSimpleDTO(savedAppointment);
    }
        catch (ResourceNotFoundException e) {

            throw e;
        } catch (Exception e) {

            throw new RuntimeException("Tạo appointment thất bại", e);
        }
        return result;
    }


    //Hàm update Appointment
    @Transactional
    public void updateAppointment(Long id, Appointment appointmentDetails){
        //Kiểm tra có tồn tại cuộc hẹn với id đó hay không

        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cuộc hẹn với id là: "+ id));

        // Nếu tồn tại tiếp tục cập nhật các trường cho cuộc hẹn cũ
        // Cập nhật bác sĩ nếu có
        if (appointmentDetails.getDoctor() != null && appointmentDetails.getDoctor().getUserId() != null) {
            User doctor = userRepository.findById(appointmentDetails.getDoctor().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + appointmentDetails.getDoctor().getUserId()));
            existingAppointment.setDoctor(doctor);
        }

        // Cập nhật thông tin bệnh nhân nếu có
        if (appointmentDetails.getPatient() != null && appointmentDetails.getPatient().getUserId() != null) {
            User patient = userRepository.findById(appointmentDetails.getPatient().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + appointmentDetails.getPatient().getUserId()));
            existingAppointment.setPatient(patient);
        }
        // Cập nhật ngày hẹn
        if(appointmentDetails.getAppointmentDate() != null){
            existingAppointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        }

        //Cập nhật giờ hẹn
        if(appointmentDetails.getAppointmentTime() != null){
            existingAppointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        }

        // Cập nhật trạng thái
        if(appointmentDetails.getStatus() != null){
            existingAppointment.setStatus(appointmentDetails.getStatus());
        }

        if (appointmentDetails.getNotes() != null) {
            existingAppointment.setNotes(appointmentDetails.getNotes());
        }

        existingAppointment.setUpdatedAt(LocalDateTime.now());

        // Cập nhật bảng AppointmentServices

        //1. Tìm và xóa các appointmentservice cũ đc lưu trong existingAppointmentService
        existingAppointment.getAppointmentServices().clear();

      //  appointmentRepository.save(existingAppointment);

        List<AppointmentServices> list = new ArrayList<>();
        //2. Tìm xem các service có tồn tại hay không
        for(AppointmentServices app : appointmentDetails.getAppointmentServices()){
        com.clinicManagement.ClinicManagement.model.Service service = serviceRepository.findById(app.getService().getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy service với id " + app.getService().getServiceId()));
            app.setService(service);
           app.setAppointment(existingAppointment);
           // appointmentServicesRepository.save(appointmentServices);

            list.add(app);
        }
        System.out.println(existingAppointment.getAppointmentId());
        appointmentServicesRepository.deleteByAppointmentId(existingAppointment.getAppointmentId());
        appointmentServicesRepository.flush();

        existingAppointment.setAppointmentServices(list);
        appointmentRepository.save(existingAppointment);

    }

    //Hàm kiểm tra tính hợp lệ của status trong appointment
    private boolean isValidStatus(String status) {
        return "Chờ khám".equals(status)
                || "Đã hoàn thành".equals(status)
                || "Đã lên lịch".equals(status)
                || "Đã hủy".equals(status);
    }

    //Hàm cập nhật trạng thái cho cuộc hẹn
    @Transactional
    public AppointmentSimpleDTO updateStatusAppointment(Long id, String Status){
        //Tìm appointment dựa trên id
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cuộc hẹn với id là: "+ id));
        System.out.println(Status);
        if (!isValidStatus(Status)) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ. " +
                    "Chỉ chấp nhận: Chờ khám, Đã hoàn thành, Đã hủy, Đã lên lịch");
        }

        appointment.setStatus(Status);
        Appointment updated = appointmentRepository.save(appointment);

        return toAppointmentSimpleDTO(updated);


    }






}
