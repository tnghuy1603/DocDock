package com.docdock.group09.user_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequest {
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNumber;
    private LocalDate dob;
    private String bloodType;
    private String allergies;
    private String pastIllness;
    private Integer height;
    private String specialty;
    private String experience;
    private String education;
}
