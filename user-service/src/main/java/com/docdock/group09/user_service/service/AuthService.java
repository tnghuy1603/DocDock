package com.docdock.group09.user_service.service;

import com.docdock.group09.user_service.dto.request.PatientSignUpRequest;
import com.docdock.group09.user_service.dto.request.SignInRequest;
import com.docdock.group09.user_service.dto.response.SignInResponse;
import com.docdock.group09.user_service.dto.response.SignUpResponse;

public interface AuthService {
    void signUp(PatientSignUpRequest request);
    SignInResponse signIn(SignInRequest request);
}
