package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.CORS.ResourceNotFoundException;
import com.clinicManagement.ClinicManagement.model.*;
import com.clinicManagement.ClinicManagement.repository.AddressRepository;
import com.clinicManagement.ClinicManagement.repository.RoleRepository;
import com.clinicManagement.ClinicManagement.repository.UserRepository;
import com.clinicManagement.ClinicManagement.repository.UserRoleRepository;
import com.clinicManagement.ClinicManagement.request.UserAddressRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleRepository roleRepository;
    // Hàm thêm mới User (role = 'staff', 'patient', 'doctor') // User patient
    @Transactional
    public void createUser(UserAddressRequest user, String strRole){
        User newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .dateOfBirth(user.getDateOfBirth())
                .idCard(user.getIdCard())
                .gender(user.getGender()).build();
        userRepository.save(newUser);
        Optional<Role> roleOptional = roleRepository.findByRoleName(strRole);
        Role role = roleOptional.get();
        UserRoleId userRoleId = new UserRoleId(newUser.getUserId(),  role.getRoleId());
        UserRole userRole = UserRole.builder()
                .userRoleId(userRoleId)
                .user(newUser)
                .role(role)
                .build();

        Address address = Address.builder()
                .street(user.getStreet())
                .ward(user.getWard())
                .district(user.getDistrict())
                .province(user.getProvince())
                .country(user.getCountry()).build();
        address.setUser(newUser);
        addressRepository.save(address);
        userRoleRepository.save(userRole);
    }

    //Hàm cập nhật thông tin User
    @Transactional
    public User updateUser(Long id, UserAddressRequest newUser){
        // kiểm tra xem id của user có tồn tại hay khng
        User oldUser = userRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException(" Không tìm thấy user"));

        // Thêm vào các trường
        if(newUser.getUsername() != null){
            oldUser.setUsername(newUser.getUsername());
        }

        if(newUser.getEmail() != null){
            oldUser.setEmail(newUser.getEmail());
        }

        if(newUser.getPhoneNumber() != null){
            oldUser.setPhoneNumber(newUser.getPhoneNumber());
        }

        if(newUser.getFullName() != null){
            oldUser.setFullName(newUser.getFullName());
        }

        if(newUser.getFullName() != null){
            oldUser.setFullName(newUser.getFullName());
        }

        if(newUser.getDateOfBirth() != null){
            oldUser.setDateOfBirth(newUser.getDateOfBirth());
        }

        if(newUser.getGender() != null){
            oldUser.setGender(newUser.getGender());
        }

        if(newUser.getIdCard() != null){
            oldUser.setIdCard(newUser.getIdCard());
        }
        userRepository.save(oldUser);
        Address address = Address.builder()
                .street(newUser.getStreet())
                .ward(newUser.getWard())
                .district(newUser.getDistrict())
                .province(newUser.getProvince())
                .country(newUser.getCountry()).build();
        if(address != null){
            Address oldAddress = addressRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

            oldAddress.setStreet(address.getStreet());
            oldAddress.setProvince(address.getProvince());
            oldAddress.setDistrict(address.getDistrict());
            oldAddress.setCountry(address.getCountry());
            oldAddress.setUser(oldUser);

            addressRepository.save(oldAddress);

        


    }return oldUser;
    }



    //Hàm tìm kiếm theo mã, tên, sdt, cccd
    @Transactional
    public User findUser(String str) {
        try {
            // Chuyển đổi String sang Long để kiểm tra ID
            Long userId = Long.valueOf(str);
            // Tìm kiếm theo ID
            Optional<User> userById = userRepository.findById(userId);
            if (userById.isPresent()) {
                return userById.get();
            }
        } catch (NumberFormatException e) {
            // Nếu không phải ID (NumberFormatException), tiếp tục tìm kiếm theo các tiêu chí khác
        }

        // Tìm kiếm theo username
        Optional<User> userByUsername = userRepository.findByUsername(str);
        if (userByUsername.isPresent()) {
            return userByUsername.get();
        }

        // Tìm kiếm theo số điện thoại
        Optional<User> userByPhonenumber = userRepository.findByPhoneNumber(str);
        if (userByPhonenumber.isPresent()) {
            return userByPhonenumber.get();
        }

        // Tìm kiếm theo ID card
        Optional<User> userByIdCard = userRepository.findByIdCard(str);
        if (userByIdCard.isPresent()) {
            return userByIdCard.get();
        }

        // Tìm kiếm theo email
        Optional<User> userByEmail = userRepository.findByEmail(str);
        if (userByEmail.isPresent()) {
            return userByEmail.get();
        }

        // Nếu không tìm thấy, ném ngoại lệ
        throw new RuntimeException("User not found");
    }

}
