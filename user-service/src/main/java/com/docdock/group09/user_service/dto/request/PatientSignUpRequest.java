package com.docdock.group09.user_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientSignUpRequest {
    private String email;
    private String password;
    private String name;
}
