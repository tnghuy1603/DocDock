package com.docdock.group09.user_service.service.impl;

import com.docdock.group09.user_service.constant.UserRole;
import com.docdock.group09.user_service.dto.request.PatientSignUpRequest;
import com.docdock.group09.user_service.dto.request.SignInRequest;
import com.docdock.group09.user_service.dto.response.SignInResponse;
import com.docdock.group09.user_service.entity.PatientEntity;
import com.docdock.group09.user_service.entity.UserEntity;
import com.docdock.group09.user_service.exception.UserServiceException;
import com.docdock.group09.user_service.repository.PatientRepository;
import com.docdock.group09.user_service.repository.UserRepository;
import com.docdock.group09.user_service.service.AuthService;
import com.docdock.group09.user_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PatientRepository patientRepository;

    @Override
    public void signUp(PatientSignUpRequest request) {
        UserEntity existingPatient = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if(existingPatient != null) {
            throw UserServiceException.buildBadRequestException("Email are already in use");
        }

        PatientEntity patientEntity = PatientEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(UserRole.PATIENT)
                .build();
        patientRepository.save(patientEntity);
    }

    @Override
    public SignInResponse signIn(SignInRequest request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> UserServiceException.buildBadRequestException("Invalid email"));
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw UserServiceException.buildBadRequestException("Invalid email or password");
        }
        String accessToken = jwtService.signToken(userEntity);
        return SignInResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
