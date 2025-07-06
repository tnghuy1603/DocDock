package com.docdock.group09.user_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequest {
    private String email;
    private String name;
    private String address;
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
