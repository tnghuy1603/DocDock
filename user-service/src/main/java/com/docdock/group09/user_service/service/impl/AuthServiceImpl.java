package com.docdock.group09.user_service.service.impl;

import com.docdock.group09.user_service.constant.UserRole;
import com.docdock.group09.user_service.dto.request.PatientSignUpRequest;
import com.docdock.group09.user_service.dto.request.SignInRequest;
import com.docdock.group09.user_service.dto.response.SignInResponse;
import com.docdock.group09.user_service.dto.response.SignUpResponse;
import com.docdock.group09.user_service.entity.UserEntity;
import com.docdock.group09.user_service.repository.UserRepository;
import com.docdock.group09.user_service.service.AuthService;
import com.docdock.group09.user_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public SignUpResponse signUp(PatientSignUpRequest request) {
        UserEntity existingEntity = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if(existingEntity != null) {
            throw new UsernameNotFoundException("Email already in use");
        }
        UserEntity newUserEntity = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(UserRole.PATIENT)
                .build();
        userRepository.save(newUserEntity);
        SignUpResponse response = new SignUpResponse();
//        response.setEmail(request.getEmail());
//        response.setPassword(newUserEntity.getPassword());
//        return response;
        return response;
    }

    @Override
    public SignInResponse signIn(SignInRequest request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        String accessToken = jwtService.signToken();
        return SignInResponse.builder()
                .accessToken(accessToken)
                .build();

    }
}
