package com.docdock.group09.user_service.service.impl;

import com.docdock.group09.user_service.constant.UserRole;
import com.docdock.group09.user_service.dto.request.UpdateUserRequest;
import com.docdock.group09.user_service.dto.response.UserResponse;
import com.docdock.group09.user_service.dto.request.UserGetRequest;
import com.docdock.group09.user_service.entity.EmployeeEntity;
import com.docdock.group09.user_service.entity.PatientEntity;
import com.docdock.group09.user_service.entity.UserEntity;
import com.docdock.group09.user_service.repository.EmployeeRepository;
import com.docdock.group09.user_service.repository.PatientRepository;
import com.docdock.group09.user_service.repository.UserRepository;
import com.docdock.group09.user_service.repository.specification.UserSpecification;
import com.docdock.group09.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;
    @Override
    public UserResponse getUserDetailsById(String id, UserRole role) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found user"));
        if (role != null && userEntity.getRole() != role) {
            throw new RuntimeException("Not found user with that role");
        }
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userEntity, userResponse);
        PatientEntity patientEntity;
        EmployeeEntity employeeEntity;
        if (UserRole.PATIENT.equals(userEntity.getRole())) {
          patientEntity = patientRepository.findById(userEntity.getId()).orElseThrow(() -> new RuntimeException("Not found user"));
          BeanUtils.copyProperties(patientEntity, userResponse);
        } else {
          employeeEntity = employeeRepository.findById(userEntity.getId()).orElseThrow(() -> new RuntimeException("Not found user"));
          BeanUtils.copyProperties(employeeEntity, userResponse);
        }
        return userResponse;
    }

    @Override
    public List<UserResponse> getUserDetailsList(UserGetRequest request) {
        Pageable pageable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit());
        Slice<UserEntity> userEntities = userRepository.findAll(UserSpecification.filterUser(request), pageable);
        List<String> userIds = userEntities.stream()
                .map(UserEntity::getId)
                .toList();
        List<PatientEntity> patientEntities = patientRepository.findAllById(userIds);
        Map<String, PatientEntity> patientMap = patientEntities.stream().collect(Collectors.toMap(PatientEntity::getId, p -> p));
        List<EmployeeEntity> employeeEntities = employeeRepository.findAllById(userIds);
        Map<String, EmployeeEntity> employeeMap = employeeEntities.stream().collect(Collectors.toMap(EmployeeEntity::getId, e -> e));
        return userEntities.stream()
                .map(userEntity -> {
                    UserResponse userResponse = new UserResponse();
                    BeanUtils.copyProperties(userEntity, userResponse);
                    if (UserRole.PATIENT.equals(userEntity.getRole())) {
                        PatientEntity patientEntity = patientMap.get(userEntity.getId());
                        if (patientEntity != null) {
                            BeanUtils.copyProperties(patientEntity, userResponse);
                        }
                    } else {
                        EmployeeEntity employeeEntity = employeeMap.get(userEntity.getId());
                        if (employeeEntity != null) {
                            BeanUtils.copyProperties(employeeEntity, userResponse);
                        }
                    }
                    return userResponse;
                })
                .toList();
    }

    @Override
    public Set<String> getUserIds(UserGetRequest request) {
        List<UserEntity> userEntities = userRepository.findAll(UserSpecification.filterUser(request));
        return userEntities.stream()
                .map(UserEntity::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        UserEntity existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new RuntimeException("Email already in use");
        }
        existingUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingUser != null) {
            throw new RuntimeException("Phone number already in use");
        }
        existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found user"));

        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        existingUser.setAddress(request.getAddress());
        existingUser.setPhoneNumber(request.getPhoneNumber());
        existingUser.setDob(request.getDob());
        if (UserRole.PATIENT.equals(existingUser.getRole())) {
            PatientEntity patientEntity = patientRepository.findById(existingUser.getId()).orElseThrow(() -> new RuntimeException("Not found user"));
            BeanUtils.copyProperties(patientEntity, existingUser);
        } else if (UserRole.DOCTOR.equals(existingUser.getRole())) {
            EmployeeEntity employeeEntity = employeeRepository.findById(existingUser.getId()).orElseThrow(() -> new RuntimeException("Not found user"));
            BeanUtils.copyProperties(request, employeeEntity);
        }
        return null;
    }


}
