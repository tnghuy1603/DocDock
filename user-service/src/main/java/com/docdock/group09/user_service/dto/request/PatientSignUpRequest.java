package com.docdock.group09.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientSignUpRequest {
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;

}
