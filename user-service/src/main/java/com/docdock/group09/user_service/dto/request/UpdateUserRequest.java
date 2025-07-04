package com.docdock.group09.user_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class UpdateUserRequest {
    private String email;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDate dob;
    private String specialty;
    private String experience;
    private String education;
}
